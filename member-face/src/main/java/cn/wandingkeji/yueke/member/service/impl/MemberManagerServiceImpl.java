/**
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 *
 * @Title: MemberManagerServiceImpl.java
 * @Package cn.wandingkeji.yueke.member.service.impl
 * @Description: TODO
 * @author: 薛展峰
 * @date: 2019年6月21日 上午9:13:07
 * @version V1.0
 */
package cn.wandingkeji.yueke.member.service.impl;

import cn.wandingkeji.common.Url;
import cn.wandingkeji.utils.constant.ConstantUtils;
import cn.wandingkeji.utils.constant.RequestType;
import cn.wandingkeji.utils.http.HttpClient;
import cn.wandingkeji.utils.http.ReturnData;
import cn.wandingkeji.yueke.company.constant.CompanyGroupType;
import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.model.CompanyGroup;
import cn.wandingkeji.yueke.company.service.CompanyService;
import cn.wandingkeji.yueke.member.constant.MemberConstant;
import cn.wandingkeji.yueke.member.mapper.FaceInfoMapper;
import cn.wandingkeji.yueke.member.mapper.MemberFaceMapper;
import cn.wandingkeji.yueke.member.mapper.MemberMapper;
import cn.wandingkeji.yueke.member.model.FaceInfo;
import cn.wandingkeji.yueke.member.model.Member;
import cn.wandingkeji.yueke.member.model.MemberAndFace;
import cn.wandingkeji.yueke.member.service.MemberManagerService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 *
 * @ClassName: MemberManagerServiceImpl
 * @Description:TODO
 * @author: 薛展峰
 * @date: 2019年6月21日 上午9:13:07
 */
@Service
public class MemberManagerServiceImpl implements MemberManagerService {

    //private static final Type[] CompanyGroup.class = null;

    private final static Logger logger = Logger.getLogger(MemberManagerServiceImpl.class);

    @Autowired
    private MemberFaceMapper meberFaceMapper;

    @Autowired
    private FaceInfoMapper faceInfoMapper;

    //@Autowired
    //private MemberManagerService memberManagerService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MemberMapper memberMapper;


    /**
     * 注册会员
     *
     * @param member  员工信息
     * @param company 公司信息
     * @param file    图片信息
     * @return
     */
    @Override
    public ReturnData<Member> registeredMember(Member member, Company company, MultipartFile file) {

        try {

            Integer count = meberFaceMapper.selectMemberCount(member.getId());
            logger.error("人数" + count);
            if (count > 0) {
                ReturnData<Member> returnData = updateMember(member, company, file);
                return returnData;
            }
        } catch (Exception e) {
            logger.error("查询人脸信息异常");
            logger.error(ExceptionUtils.getMessage(e));
            e.printStackTrace();
            return ConstantUtils.printErrorMessage();
        }

        Map<String, Object> paramMap = new HashMap<>();

        Map<String, String> headers = new HashMap<>();
        headers.put(MemberConstant.MEMBER_REQUSET_AUTHORIZATION, "Bearer " + company.getAuthToken());

        if (null == file) {

            logger.error("上传图片为空 ！");
            return ConstantUtils.printErrorMessage("上传图片为空 ");

        } else {

            paramMap.put(MemberConstant.MEMBER_REQUSET_AVATARS, new File(createTempImage(file)));
        }
        String groupJSONString = queryCustomerGroups(headers);


        logger.info("会员组 信息" + groupJSONString);
        List<CompanyGroup> companyGroupList = JSONArray.parseArray(groupJSONString, CompanyGroup.class);


        CompanyGroup baseGroup = null;

        for (CompanyGroup companyGroup : companyGroupList) {

            //找到会员组类型
            if (companyGroup.getGroupType().equals(CompanyGroupType.MEMBER_TYPE)) {

                baseGroup = companyGroup;
                paramMap.put(MemberConstant.MEMBER_REQUSET_CUSTOMER_GROUP_ID, companyGroup.getId());
            }

        }
        Member mem = null;
        String sex = member.getGender();
        String name = member.getName();
        String phone = member.getPhone();
        String birthday = member.getBirthday();
        try {

            //查询会员信息，如果传递了信息，就从传进来的信息中取，如果没有就从数据库中取
            mem = memberMapper.selectMemberById(member.getId());
            if (mem == null) {
                return ConstantUtils.printErrorMessage("该用户不是会员");
            } else {
                //性别默认是男
                sex = mem.getSex();
                System.out.println(paramMap);
                if (sex == null) {
                    sex = "1";
                } else if (sex.equals("MALE")) {
                    sex = "1";
                } else if (sex.equals("FEMAIL")) {
                    sex = "0";
                } else {
                    sex = "1";
                }

                if (name == null) {
                    name = mem.getName();
                }

                if (phone == null) {
                    phone = mem.getPhone();
                }

                if (birthday == null) {
                    String year = mem.getYear();
                    String month = mem.getMonth();
                    String day = mem.getDay();
                    birthday = ConstantUtils.formatStringToDate(year, month, day, "-");
                }

            }
        } catch (Exception e) {
            logger.error("用户查询信息失败");
            logger.error(ExceptionUtils.getMessage(e));
            return ConstantUtils.printErrorMessage();
        }

        //请求参数
        paramMap.put(MemberConstant.MEMBER_REQUEST_NAME, name);
        paramMap.put(MemberConstant.MEMBER_REQUSET_PHONE, phone);
        paramMap.put(MemberConstant.MEMBER_REQUEST_GENDER, sex);
        paramMap.put(MemberConstant.MEMBER_REQUEST_BIRTHDAY, birthday);

        logger.info("注册会员请求如参数 " + paramMap);

        //发起POST文件请求注册会员
        String postPara = HttpClient.sendPara(Url.COMPANY_CUSTOMERS_URL, paramMap, headers, RequestType.REQUEST_POST);
        JSONObject jsonObj = JSONObject.parseObject(postPara);
        logger.info("约客返回 jsonObj is " + jsonObj);
        Object object = jsonObj.get("errors");
        if (object == null || object.equals("")) {
            try {

                //人脸信息
                FaceInfo faceInfo = new FaceInfo();
                faceInfo.setId(ConstantUtils.getUUID());
                faceInfo.setCustomerId(jsonObj.getString("id"));
                faceInfo.setMemberGroupId(jsonObj.getString("customer_group_id"));
                faceInfo.setGroupId(baseGroup.getGroupId());
                faceInfo.setPersonId(jsonObj.getString("person_id"));
                faceInfo.setOpenId(mem.getOpenid());

                //获取JSON数组中的图片 修改的最后一张图片
                String avatars = jsonObj.getString("avatars");
                JSONArray parseArray = JSONArray.parseArray(avatars);
                JSONObject jsonObject = parseArray.getJSONObject(parseArray.size() - 1);
                String faceUrl = jsonObject.getString("url");

                faceInfo.setFaceUrl(faceUrl);

                faceInfoMapper.insert(faceInfo);

                //会员与人脸关联
                MemberAndFace memberAndFace = new MemberAndFace();
                memberAndFace.setFaceId(faceInfo.getId());
                memberAndFace.setMemberId(member.getId());
                memberAndFace.setId(ConstantUtils.getUUID());


                //将会员信息写入数据库
                meberFaceMapper.insert(memberAndFace);
                return ConstantUtils.printSuccessMessage("注册成功", member);

            } catch (Exception e) {
                logger.error(ExceptionUtils.getMessage(e));
                logger.error("会员信息注册失败");
                return ConstantUtils.printErrorMessage();
            }

        } else {

            JSONArray jsonArray = jsonObj.getJSONArray("errors");
            String code = jsonArray.getJSONObject(0).getString("code");
            return ConstantUtils.printErrorMessage(code, jsonArray.getJSONObject(0).getString("title_zh") + "," + jsonArray.getJSONObject(0).getString("detail"));
        }
    }


    /**
     * @param member
     * @param company
     * @param file
     * @return
     */
    @Override
    public ReturnData<Member> updateMember(Member member, Company company, MultipartFile file) {

        Map<String, Object> paramMap = new HashMap<>();
        logger.info("修改人脸信息");

        Map<String, String> headers = new HashMap<>();
        headers.put(MemberConstant.MEMBER_REQUSET_AUTHORIZATION, "Bearer " + company.getAuthToken());

        String replace = null;

        //文件不为空
        if (file != null) {
            String originalFilename = file.getOriginalFilename();

            //将上传的文件  在服务器中进行  临时存储  并发往后台
            String pathSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));


            replace = "/data/wwwroot/default/" + "pic" + pathSuffix;
            logger.info("pic replace :" + replace);

            FileOutputStream outputStream = null;
            try {
                byte[] bytes = file.getBytes();
                outputStream = new FileOutputStream(replace);
                outputStream.write(bytes);

                paramMap.put("avatars[]", new File(replace));
            } catch (FileNotFoundException e) {
                logger.error(ExceptionUtils.getMessage(e));
                logger.error("中间站：文件找不到，保存失败");
            } catch (IOException e) {
                logger.error(ExceptionUtils.getMessage(e));
                logger.error("中间站：图片保存失败");
            } finally {
                if (null != outputStream) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        logger.error("中间站：图片保存失败");
                        logger.error(ExceptionUtils.getMessage(e));
                    }
                }
            }
        }

        //获取人脸信息  customer_id 根据会员ID查询
        FaceInfo faceInfo = null;
        try {

            logger.info("查询人脸信息 customer_id is :" + member.getId());

            //查询该用户人脸信息
            faceInfo = faceInfoMapper.selectMemberFaceInfo(member.getId());


            if (faceInfo == null) {
                return ConstantUtils.printErrorMessage("查询人脸信息失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("查询人脸信息失败");
            logger.error(ExceptionUtils.getMessage(e));
        }
        //查询会员组
        String groupJSONString = company.getGroup();

        //获取公司  下的组  进行JSON转换
        List<CompanyGroup> companyGroupList = JSONArray.parseArray(groupJSONString, CompanyGroup.class);

        CompanyGroup baseGroup = null;

        //如果是会员类型，将参数封装
        for (CompanyGroup companyGroup : companyGroupList) {

            if (companyGroup.getGroupType().equals(CompanyGroupType.MEMBER_TYPE)) {

                baseGroup = companyGroup;
                paramMap.put(MemberConstant.MEMBER_REQUSET_CUSTOMER_GROUP_ID, companyGroup.getId());
            }
        }

        Member mem = null;
        String sex = member.getGender();
        String name = member.getName();
        String phone = member.getPhone();
        try {

            logger.info("查询会员信息");

            mem = memberMapper.selectMemberById(member.getId());
            if (mem == null) {
                return ConstantUtils.printErrorMessage("该用户不是会员");
            } else {

                //1 是男 0  是女
                sex = mem.getSex();
                System.out.println(paramMap);
                if (sex == null) {
                    sex = "1";
                } else if (sex.equals("MALE")) {
                    sex = "1";
                } else if (sex.equals("FEMAIL")) {
                    sex = "0";
                } else {
                    sex = "1";
                }

                //如果没传 使用查询出来的姓名
                if (name == null) {
                    name = mem.getName();
                }

                if (phone == null) {
                    phone = mem.getPhone();
                }
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getMessage(e));
            logger.error("查询会员失败");
            return ConstantUtils.printErrorMessage();
        }
        logger.info("人脸信息" + faceInfo);
        //请求参数
        paramMap.put(MemberConstant.MEMBER_REQUEST_NAME, name);
        paramMap.put(MemberConstant.MEMBER_REQUSET_PHONE, phone);
        paramMap.put(MemberConstant.MEMBER_REQUEST_GENDER, sex);
        paramMap.put(MemberConstant.MEMBER_REQUEST_BIRTHDAY, "");
        logger.info("avatar_url is " + faceInfo.getFaceUrl());
        paramMap.put(MemberConstant.MEMBER_REQUSET_AVATARS_URL, faceInfo.getFaceUrl());


        //发起PUTCH文件请求，使用人脸信息  customer_id 发起请求
        String postPara = HttpClient.sendPUTCHPara(Url.COMPANY_CUSTOMERS_URL + "/" + faceInfo.getCustomerId(), paramMap, headers);
        JSONObject jsonObj = JSONObject.parseObject(postPara);

        Object object = jsonObj.get("errors");

        //如果错误信息为空，就执行更新数据库操作
        if (object == null || object.equals("")) {
            //更新数据库，会员人脸信息
            try {
                if (faceInfo != null) {
                    faceInfo.setMemberGroupId(member.getMemberGroupId());
                    faceInfo.setPersonId(jsonObj.getString("person_id"));
                    String avatars = jsonObj.getString("avatars");
                    JSONArray parseArray = JSONArray.parseArray(avatars);
                    faceInfo.setFaceUrl(parseArray.getJSONObject(0).getString("url"));

                    logger.info("修改人脸信息");
                    faceInfoMapper.update(faceInfo);
                }
            } catch (Exception e) {
                logger.error(ExceptionUtils.getMessage(e));
                logger.error("修改人脸信息失败");
                return ConstantUtils.printErrorMessage();
            }
        } else {
            logger.info("第三方，错误信息：" + jsonObj);
            JSONArray jsonArray = jsonObj.getJSONArray("errors");
            String code = jsonArray.getJSONObject(0).getString("code");
            return ConstantUtils.printErrorMessage(code, jsonArray.getJSONObject(0).getString("title_zh") + "," + jsonArray.getJSONObject(0).getString("detail"));

        }
        return ConstantUtils.printSuccessMessage("该用户已注册过人脸信息，已修改成功", member);
    }


    /**
     * 创建临时图片文件
     *
     * @param file 文件
     * @return 生成文件路径
     */
    private String createTempImage(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        //将上传的文件  在服务器中进行  临时存储  并发往后台
        String pathSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String imagePath = Url.IMG_PATH + "pic" + pathSuffix;
        logger.info("pic replace :" + imagePath);

        FileOutputStream outputStream = null;
        try {
            byte[] bytes = file.getBytes();
            outputStream = new FileOutputStream(imagePath);
            outputStream.write(bytes);

        } catch (FileNotFoundException e) {
            logger.error(ExceptionUtils.getMessage(e));
            logger.error("中间站：文件找不到，保存失败");
            return "";

        } catch (IOException e) {
            logger.error(ExceptionUtils.getMessage(e));
            logger.error("中间站：图片保存失败");
            return "";

        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("中间站：图片保存失败");
                    logger.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return imagePath;
    }


    /**
     *  查询会员组
     * @param headers token信息
     * @return 会员组
     */
    private String queryCustomerGroups(Map<String, String> headers) {

        try {
            String postPara = HttpClient.doGet(Url.CUSTOMER_GROUPS_URL, headers, null);
            return postPara;
        } catch (Exception e) {

            logger.error("查询会员组信息失败 ！");
            return "";
        }

    }


}
