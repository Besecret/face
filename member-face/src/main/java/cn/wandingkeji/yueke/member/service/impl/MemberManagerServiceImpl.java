package cn.wandingkeji.yueke.member.service.impl;

import cn.wandingkeji.common.Constant;
import cn.wandingkeji.common.Url;
import cn.wandingkeji.utils.constant.ConstantUtils;
import cn.wandingkeji.utils.constant.RequestType;
import cn.wandingkeji.utils.http.HttpClient;
import cn.wandingkeji.utils.http.ReturnData;
import cn.wandingkeji.yueke.company.constant.CompanyGroupType;
import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.model.CompanyGroup;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 会员注册实现类
 *
 * @author w.d.k.j
 */
@Service
@Slf4j
public class MemberManagerServiceImpl implements MemberManagerService {


    @Resource
    private MemberFaceMapper meberFaceMapper;

    @Resource
    private FaceInfoMapper faceInfoMapper;

    @Resource
    private MemberMapper memberMapper;


    /**
     * 注册会员
     *
     * @param member  员工信息
     * @param company 公司信息
     * @param file    图片信息
     * @return result
     */
    @Override
    public ReturnData<Member> registeredMember(Member member, Company company, MultipartFile file) {

        log.info("注册会员，入参数 member" + member);
        Integer count = meberFaceMapper.countMemberById(member.getId());
        log.info("人数" + count);
        if (count > 0) {
            log.info("会员已存在,更新会员信息");

            return updateMember(member, company, file);
        }
        Map<String, Object> paramMap = new HashMap<>(16);
        Map<String, String> headers = new HashMap<>(16);

        headers.put(MemberConstant.MEMBER_REQUSET_AUTHORIZATION, "Bearer " + company.getAuthToken());
        if (null == file) {
            log.error("上传图片为空 ！");
            return ConstantUtils.printErrorMessage("上传图片为空 ");

        } else {

            paramMap.put(MemberConstant.MEMBER_REQUSET_AVATARS, new File(createTempImage(file)));
        }
        String groupInfo = queryCustomerGroups(headers);
        log.info("会员组 信息" + groupInfo);

        try {
            List<CompanyGroup> companyGroupList = JSONArray.parseArray(groupInfo, CompanyGroup.class);
            //员工注册&店员注册 0 ->会员 ; 1->雇员
            String registerType = member.getType() == null ? CompanyGroupType.MEMBER_TYPE :
                    CompanyGroupType.EMPLOYEE_TYPE;

            companyGroupList.forEach(group -> {
                if (group.getGroupType().equals(registerType)) {
                    paramMap.put(MemberConstant.MEMBER_REQUSET_CUSTOMER_GROUP_ID, group.getId());
                }
            });
        }
        catch (Exception e){
            log.error("json转换失败");
            return ConstantUtils.printErrorMessage("json转换失败");
        }

        setMemberInfo(member, paramMap);
        log.info("注册会员请求如参数 " + paramMap);
        //发起POST文件请求注册会员
        String postPara = HttpClient.sendPara(Url.COMPANY_CUSTOMERS_URL, paramMap, headers, RequestType.REQUEST_POST);
        JSONObject jsonObj = JSONObject.parseObject(postPara);
        log.info("  注册返回 jsonObj is " + jsonObj);

        if (StringUtils.isEmpty(jsonObj.getString(Constant.YK_ERROR_CODE))) {
            log.info("注册成功，开始数据同步。。");
            int num = insertFaceInfo(jsonObj, member);
            if (num > 0) {
                return ConstantUtils.printSuccessMessage("注册成功", member);
            } else {
                return ConstantUtils.printErrorMessage("注册成功,入库失败");
            }

        } else {

            JSONArray jsonArray = jsonObj.getJSONArray("errors");
            String code = jsonArray.getJSONObject(0).getString("code");
            return ConstantUtils.printErrorMessage(code, jsonArray.getJSONObject(0).getString("title_zh") + "," + jsonArray.getJSONObject(0).getString("detail"));
        }
    }


    /**
     * 更新会员信息
     *
     * @param member  会员
     * @param company 公司信息
     * @param file    图片
     * @return result
     */
    @Override
    public ReturnData<Member> updateMember(Member member, Company company, MultipartFile file) {


        Map<String, Object> paramMap = new HashMap<>(16);
        log.info("修改会员，入参数 member" + member);

        Map<String, String> headers = new HashMap<>(16);

        headers.put(MemberConstant.MEMBER_REQUSET_AUTHORIZATION, "Bearer " + company.getAuthToken());
        paramMap.put(MemberConstant.MEMBER_REQUSET_AVATARS, new File(createTempImage(file)));

        log.info("查询人脸信息 customer_id is :" + member.getId());

        FaceInfo faceInfo = faceInfoMapper.selectMemberFaceInfo(member.getId());
        if (faceInfo == null) {
            return ConstantUtils.printErrorMessage("查询人脸信息失败");
        } else {
            //
            int gender = member.getGender() == null ?
                    faceInfo.getGender() : member.getGender();
            String name = member.getName() == null ? faceInfo.getName() : member.getName();
            String phone = member.getPhone() == null ? faceInfo.getPhone() : member.getPhone();

            String birthday = member.getBirthday() == null ? faceInfo.getBirthday()
                    : member.getBirthday();

            String registerType = member.getType() == null ? CompanyGroupType.MEMBER_TYPE :
                    CompanyGroupType.EMPLOYEE_TYPE;

            String groupInfo = queryCustomerGroups(headers);
            log.info("会员组 信息" + groupInfo);
            List<CompanyGroup> companyGroupList = JSONArray.parseArray(groupInfo, CompanyGroup.class);

            companyGroupList.forEach(group -> {
                if (group.getGroupType().equals(registerType)) {
                    paramMap.put(MemberConstant.MEMBER_REQUSET_CUSTOMER_GROUP_ID, group.getId());
                }
            });

            paramMap.put(MemberConstant.MEMBER_REQUSET_CUSTOMER_GROUP_ID, faceInfo.getGroupId());
            //历史照片
            paramMap.put(MemberConstant.MEMBER_REQUSET_AVATARS_URL, faceInfo.getFaceUrl());
            paramMap.put(MemberConstant.MEMBER_REQUEST_NAME, name);
            paramMap.put(MemberConstant.MEMBER_REQUSET_PHONE, phone);
            paramMap.put(MemberConstant.MEMBER_REQUEST_GENDER, gender);
            paramMap.put(MemberConstant.MEMBER_REQUEST_BIRTHDAY, birthday);

        }
        log.info("更新请求参数 paramMap"+paramMap);
        //发起PUTCH文件请求，使用人脸信息  customer_id 发起请求
        String postPara = HttpClient.sendPUTCHPara(Url.COMPANY_CUSTOMERS_URL + "/" + faceInfo.getCustomerId(), paramMap, headers);
        JSONObject jsonObj = JSONObject.parseObject(postPara);
        log.info("约科更新返回 jsonObj" + jsonObj);

        if (StringUtils.isEmpty(jsonObj.getString(Constant.YK_ERROR_CODE))) {

            faceInfo.setPersonId(jsonObj.getString("person_id"));
            faceInfo.setFaceUrl(jsonObj.getString("avatars"));
            int num = faceInfoMapper.update(faceInfo);
            if (num > 0) {
                return ConstantUtils.printSuccessMessage("已修改成功", member);
            } else {
                return ConstantUtils.printErrorMessage("修改成功，同步入库失败");
            }

        } else {
            log.info("第三方，错误信息：" + jsonObj);
            JSONArray jsonArray = jsonObj.getJSONArray("errors");
            String code = jsonArray.getJSONObject(0).getString("code");
            return ConstantUtils.printErrorMessage(code, jsonArray.getJSONObject(0).getString("title_zh") + "," + jsonArray.getJSONObject(0).getString("detail"));

        }

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
        log.info("pic replace :" + imagePath);

        FileOutputStream outputStream = null;
        try {
            byte[] bytes = file.getBytes();
            outputStream = new FileOutputStream(imagePath);
            outputStream.write(bytes);

        } catch (FileNotFoundException e) {
            log.error(ExceptionUtils.getMessage(e));
            log.error("中间站：文件找不到，保存失败");
            return "";

        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            log.error("中间站：图片保存失败");
            return "";

        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("中间站：图片保存失败");
                    log.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return imagePath;
    }


    /**
     * 同步人脸信息入库
     *
     * @param jsonObj 约克信息
     * @param member  会员信息
     * @return 数据受影响行
     */
    private int insertFaceInfo(JSONObject jsonObj, Member member) {


        //人脸信息
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setId(ConstantUtils.getUUID());
        faceInfo.setCustomerId(jsonObj.getString("id"));
        faceInfo.setGroupId(jsonObj.getString("customer_group_id"));
        faceInfo.setPersonId(jsonObj.getString("person_id"));
        faceInfo.setOpenId(member.getOpenid());
        faceInfo.setName(jsonObj.getString("name"));
        faceInfo.setPhone(jsonObj.getString("phone"));
        faceInfo.setGender(Integer.parseInt(jsonObj.getString("gender")));
        faceInfo.setBirthday(jsonObj.getString("birthday").split("T")[0]);
        String avatars = jsonObj.getString("avatars");
        faceInfo.setFaceUrl(avatars);


        int num = faceInfoMapper.insert(faceInfo);
        if (num < 1) {
            log.error("face info 同步失败");
        }
        //会员与人脸关联
        MemberAndFace memberAndFace = new MemberAndFace();
        memberAndFace.setFaceId(faceInfo.getId());
        memberAndFace.setMemberId(member.getId());
        memberAndFace.setId(ConstantUtils.getUUID());

        int faceNum = meberFaceMapper.insert(memberAndFace);
        if (faceNum < 1) {
            log.error("memberAndFace 同步失败");
        }

        return num * faceNum;
    }


    /**
     * 查询会员组
     *
     * @param headers token信息
     * @return 会员组
     */
    private String queryCustomerGroups(Map<String, String> headers) {

        try {
            return HttpClient.doGet(Url.CUSTOMER_GROUPS_URL, headers, null);

        } catch (Exception e) {

            log.error("查询会员组信息失败 ！");
            return "";
        }

    }

    /**
     * 查询会员信息
     *
     * @param regMember 会员
     */
    private void setMemberInfo(Member regMember, Map<String, Object> paramMap) {
        Member member = memberMapper.selectMemberById(regMember.getId());
        if (member != null) {
            //性别默认为1(男)

            Integer sex = regMember.getGender() == null ?
                    ("MALE".equals(member.getSex()) ? 1 : 0) : regMember.getGender();
            String name = regMember.getName() == null ? member.getName() : regMember.getName();
            String phone = regMember.getPhone() == null ? member.getPhone() : regMember.getPhone();
            String year = member.getYear();
            String month = member.getMonth();
            String day = member.getDay();
            String birthday = regMember.getPhone() == null ?
                    ConstantUtils.formatStringToDate(year, month, day, "-") : regMember.getBirthday();

            paramMap.put(MemberConstant.MEMBER_REQUEST_NAME, name);
            paramMap.put(MemberConstant.MEMBER_REQUSET_PHONE, phone);
            paramMap.put(MemberConstant.MEMBER_REQUEST_GENDER, sex);
            paramMap.put(MemberConstant.MEMBER_REQUEST_BIRTHDAY, birthday);
        }
    }
}
