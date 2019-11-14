package cn.wandingkeji.yueke.callback.service.impl;

import cn.wandingkeji.common.Constant;
import cn.wandingkeji.common.Url;
import cn.wandingkeji.utils.constant.ConstantUtils;
import cn.wandingkeji.utils.http.HttpClient;
import cn.wandingkeji.yueke.callback.mapper.CustomerInfoMapper;
import cn.wandingkeji.yueke.callback.mapper.MerchantTemplateMapper;
import cn.wandingkeji.yueke.callback.mapper.MerchantTokenMapper;
import cn.wandingkeji.yueke.callback.model.EventModel;
import cn.wandingkeji.yueke.callback.model.MerchantTemplate;
import cn.wandingkeji.yueke.callback.model.TemplateData;
import cn.wandingkeji.yueke.callback.model.WxTemplate;
import cn.wandingkeji.yueke.callback.service.MessageService;
import cn.wandingkeji.yueke.company.model.MerchantToken;
import cn.wandingkeji.yueke.member.mapper.FaceInfoMapper;
import cn.wandingkeji.yueke.member.mapper.MemberMapper;
import cn.wandingkeji.yueke.member.model.FaceInfo;
import cn.wandingkeji.yueke.member.model.MemberMesTemplate;
import cn.wandingkeji.yueke.shop.mapper.ShopMapper;
import cn.wandingkeji.yueke.shop.model.Shop;
import com.alibaba.fastjson.JSONObject;
import com.weupay.common.util.StringUtils;
import com.weupay.pay.api.protocol.AccessTokenRes;
import com.weupay.pay.api.service.GetTokenService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息推送service
 *
 * @author w.d.k.j
 */

@Service
public class MessageServiceImpl implements MessageService {

    private Logger logger = Logger.getLogger(MessageServiceImpl.class);

    @Resource
    private FaceInfoMapper faceInfoMapper;

    @Resource
    private ShopMapper shopMapper;

    @Resource
    private MerchantTemplateMapper merchantsTemplateMapper;

    @Resource
    private MerchantTokenMapper merchantTokenMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private CustomerInfoMapper customerMapper;


    @Override
    public Object pushMessage(EventModel eventModel) {


        logger.info("获取商店信息, shop_id :" + eventModel.getShopId());
        Shop shop = shopMapper.selectShopByShopId(eventModel.getShopId().toString());
        if (shop == null) {
            logger.error("获取店铺信息失败");
            return ConstantUtils.printErrorMessage(" 获取店铺信息失败! ");
        }

        String customerId = eventModel.getCustomer().getId().toString();
        logger.info("获取会员信息, customer_id ：" + customerId);
        //查询到会员
        FaceInfo memberFaceInfo = faceInfoMapper.selectFaceInfoByCustomerId(customerId);

        if (memberFaceInfo == null) {

            logger.error("获取人脸信息失败!");
            return ConstantUtils.printErrorMessage(" 获取人脸信息失败! ");
        } else {

            String companyId = shop.getCompanyId();
            String merchantId = shop.getMerchantId();
            logger.info("设备返回的会员id" + customerId + "查询到的会员ID" + memberFaceInfo.getCustomerId() + "会员的OPENID"
                    + memberFaceInfo.getOpenId() + " 商户ID ：" + merchantId);

            //向会员推送
            if (customerId.equals(memberFaceInfo.getCustomerId())) {

                logger.info("------ 开始发送消息给顾客 Start !---------");
                Boolean flag = sendWxMessage(goShopToCustomerTemplate(companyId, customerId), getToken(merchantId));
                logger.info("------ 发送消息给顾客结束 End !---------");
                if (!flag) {
                    return ConstantUtils.printErrorMessage(" 推送顾客信息失败! ");
                }


                //雇员发送 TBD 此处功能待优化
                logger.info("-----------开始发送消息给雇员 Start ! -------------");
                String storeId = shop.getStoreId();
                logger.info("storeId :" + storeId);
                // employeeOpenId 赋值
                String employeeOpenId = "";
                sendWxMessage(goShopToEmployeeTemplate(companyId, customerId, employeeOpenId, storeId), getToken(merchantId));
                logger.info("----------- 发送消息给雇员 ENd ! -------------");
            }
        }

        return ConstantUtils.printSuccessMessage(" 消息推送成功 ! ", null);
    }

    @Override
    public int synCustomerInfo(EventModel event) {

        if (customerMapper.queryCustomerByCustomerId(event) != null) {

            logger.info("更新入库。。");
            return customerMapper.updateCustomerInfoById(event);
        } else {
            logger.info("保存入库。。");
            return customerMapper.saveCustomerInfo(event);
        }


    }


    /**
     * 发送消息
     *
     * @param wxTemplate  模板消息
     * @param accessToken token
     */
    private Boolean sendWxMessage(WxTemplate wxTemplate, String accessToken) {

        String jsonString = JSONObject.toJSONString(wxTemplate);
        logger.info("发送的模板信息为" + jsonString);
        //向微信发起推送模板请求
        String doPost = HttpClient.doPost(Url.WX_SEND_TEMPLATE_URL + accessToken, JSONObject.parseObject(jsonString), null);

        JSONObject message = JSONObject.parseObject(doPost);

        logger.info(message);

        if (Integer.parseInt(message.getString(Constant.WX_STATUS_CODE)) == 0) {

            logger.info("发送成功");
            return true;

        } else {

            String errmsg = message.getString(Constant.WX_STATUS_MSG);
            logger.info(errmsg);
            return false;
        }

    }


    /**
     * 获取会员模板信息
     *
     * @param customerId 人脸识别id
     * @return tempalte
     */
    private MemberMesTemplate getMemberInfo(String customerId) {
        return memberMapper.selectMemberByCustomerId(customerId);
    }


    /**
     * 到店提醒to雇员模板
     *
     * @param companyId  商户id
     * @param customerId 雇员人脸id
     * @return wxTemplate
     */
    private WxTemplate goShopToEmployeeTemplate(String companyId, String customerId, String employeeOpenId, String storeId) {

        logger.info("customerId is " + customerId);
        MemberMesTemplate member = getMemberInfo(customerId);
        if (member == null) {
            logger.error("获取会员信息失败");
            return null;
        }
        //setToUser
        if (!StringUtils.isEmpty(employeeOpenId)) {
            member.setOpenId(employeeOpenId);
        }
        //获取当前门店名称
        if (!StringUtils.isEmpty(storeId)) {
            String name = shopMapper.selectStoreNameByStoreId(Integer.parseInt(storeId));
            logger.info("门店名称 ：" + name);
            member.setStoreName(name);
        }

        //获取微信消息模板
        logger.info("merchant id :" + companyId + "template id :" + Constant.TEMPLATE_GO_SHOP_EMPLOYEE);
        MerchantTemplate template = getWxMessageTemplate(companyId, Constant.TEMPLATE_GO_SHOP_EMPLOYEE);
        if (null != template) {
            member.setTemplateId(template.getThirdTemplateId());
            logger.info("发送给雇员,使用模板id为" + member.getTemplateId());
        }
        //设置跳转链接
        member.setUrl("http://weixin.qq.com/download");
        return initGoShopToEmployeeTemplate(member);
    }

    /**
     * 到店提醒to雇员模板
     *
     * @param companyId  商户id
     * @param customerId 雇员人脸id
     * @return wxTemplate
     */
    private WxTemplate goShopToCustomerTemplate(String companyId, String customerId) {

        logger.info("customerId is " + customerId);
        MemberMesTemplate member = getMemberInfo(customerId);
        if (member == null) {
            logger.error("获取会员信息失败");
            return null;
        }
        //获取微信消息模板
        logger.info("merchant id :" + companyId + "template id :" + Constant.TEMPLATE_GO_SHOP_CUSTOMER);
        MerchantTemplate template = getWxMessageTemplate(companyId, Constant.TEMPLATE_GO_SHOP_CUSTOMER);
        if (null != template) {
            member.setTemplateId(template.getThirdTemplateId());
            logger.info("顾客进店，微信公众号发送消息给顾客,使用模板id为" + member.getTemplateId());
        }
        //设置跳转链接
        member.setUrl("http://weixin.qq.com/download");
        return initGoShopToCustomerTemplate(member);
    }


    /**
     * 微信公共模板
     *
     * @param companyId  公司id
     * @param templateId 模板id
     * @return 模板
     */
    private MerchantTemplate getWxMessageTemplate(String companyId, String templateId) {

        List<MerchantTemplate> templateList = merchantsTemplateMapper.selectMerchantTemplateByCompanyId(companyId);
        //会员到店
        if (templateList != null && !templateList.isEmpty()) {

            return templateList.stream().filter(template -> template.getTemplateId().equals(templateId)).collect(Collectors.toList()).get(0);
        } else {
            logger.error("query merchant tempalte is fail !");
            return null;
        }
    }


    /**
     * 会员到店
     * {{first.DATA}}
     * 会员信息：{{keyword1.DATA}}
     * 余额积分：{{keyword2.DATA}}
     * 联系信息：{{keyword3.DATA}}
     * 到店信息：{{keyword4.DATA}}
     * 专属客服：{{keyword5.DATA}}
     * {{remark.DATA}}
     *
     * @param member 会员信息
     * @return wxTemplate 微信模板
     */
    private WxTemplate initGoShopToEmployeeTemplate(MemberMesTemplate member) {

        WxTemplate wxEmployeeTemplateData = new WxTemplate();

        Map<String, TemplateData> dataMap = new HashMap<>(16);

        wxEmployeeTemplateData.setTouser(member.getOpenId());
        wxEmployeeTemplateData.setTemplate_id(member.getTemplateId());

        wxEmployeeTemplateData.setUrl(member.getUrl());

        //小程序待定
        WxTemplate.Miniprogram mimiprogram = wxEmployeeTemplateData.new Miniprogram();
        mimiprogram.setAppid("");
        mimiprogram.setPagepath("");

        TemplateData first = new TemplateData();
        first.setColor("");
        first.setValue("您好！现在有一名顾客入店");

        TemplateData keyword1 = new TemplateData();
        keyword1.setColor("");
        keyword1.setValue(member.getLevelName());

        //balance + bouns
        TemplateData keyword2 = new TemplateData();
        keyword2.setColor("");
        keyword2.setValue(member.getBalance());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        TemplateData keyword3 = new TemplateData();
        keyword3.setColor("");
        if (member.getPayTimeLatest() != null) {
            keyword3.setValue(formatter.format(member.getPayTimeLatest()));
        }
        TemplateData keyword4 = new TemplateData();
        keyword4.setColor("");
        keyword4.setValue(member.getStoreNameLatest());

        TemplateData keyword5 = new TemplateData();
        keyword5.setColor("");
        keyword5.setValue(member.getStoreNameLatest());

        TemplateData remark = new TemplateData();
        remark.setColor("");
        remark.setValue("查看详情");

        dataMap.put("first", first);
        dataMap.put("keyword1", keyword1);
        dataMap.put("keyword2", keyword2);
        dataMap.put("keyword3", keyword3);
        dataMap.put("keyword4", keyword4);
        dataMap.put("keyword5", keyword4);
        dataMap.put("remark", keyword4);

        wxEmployeeTemplateData.setData(dataMap);
        return wxEmployeeTemplateData;
    }


    /**
     * {{first.DATA}}
     * 顾客名称：{{keyword1.DATA}}
     * 门店名称：{{keyword2.DATA}}
     * 进店时间：{{keyword3.DATA}}
     * {{remark.DATA}}
     *
     * @param member 会员信息入参数
     * @return wxTemplate
     */
    private WxTemplate initGoShopToCustomerTemplate(MemberMesTemplate member) {

        WxTemplate wxEmployeeTemplateData = new WxTemplate();

        Map<String, TemplateData> dataMap = new HashMap<>(16);

        wxEmployeeTemplateData.setTouser(member.getOpenId());
        wxEmployeeTemplateData.setTemplate_id(member.getTemplateId());

        wxEmployeeTemplateData.setUrl(member.getUrl());

        //小程序待定 WxTemplate.Miniprogram mimiprogram = wxEmployeeTemplateData.new Miniprogram();

        TemplateData first = new TemplateData();
        first.setColor("");
        first.setValue("欢迎光临");

        TemplateData keyword1 = new TemplateData();
        keyword1.setColor("");
        keyword1.setValue(member.getMemberName());

        //balance + bouns
        TemplateData keyword2 = new TemplateData();
        keyword2.setColor("");
        keyword2.setValue(member.getBalance());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TemplateData keyword3 = new TemplateData();
        keyword3.setColor("");
        keyword3.setValue(df.format(new Date()));

        TemplateData remark = new TemplateData();
        remark.setColor("");
        remark.setValue("欢迎再次光临");

        dataMap.put("first", first);
        dataMap.put("keyword1", keyword1);
        dataMap.put("keyword2", keyword2);
        dataMap.put("keyword3", keyword3);
        wxEmployeeTemplateData.setData(dataMap);
        return wxEmployeeTemplateData;
    }

    /**
     * 获取商户微信公众号token
     *
     * @param merchantId 商户id
     * @return token
     */
    private String getToken(String merchantId) {

        GetTokenService getTokenService = new GetTokenService();
        //查询商户 token 4 微信小程序
        MerchantToken merchantToken = merchantTokenMapper.selectMerchantToken(merchantId, "4");

        String getTokenUrl = String.format(Url.WX_ACCESS_TOKEN_URL, merchantToken.getTokenId());
        AccessTokenRes accessTokenRes = getTokenService.request(getTokenUrl);
        logger.info("获取token值为：    " + accessTokenRes);

        return accessTokenRes.getAccessToken();
    }
}
