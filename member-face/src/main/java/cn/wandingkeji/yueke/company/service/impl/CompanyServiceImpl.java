package cn.wandingkeji.yueke.company.service.impl;

import cn.wandingkeji.common.Url;
import cn.wandingkeji.utils.http.HttpClient;
import cn.wandingkeji.yueke.company.mapper.CompanyMapper;
import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.model.CompanyGroup;
import cn.wandingkeji.yueke.company.service.CompanyService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 公司&管理员登陆实现
 *
 * @author w.d.k.j
 */

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {


    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Company login(String mid) {

        Company company = companyMapper.selectCompanyByMid(mid);
        // 组织参数
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("admin", company);

        log.info("compamy" + company);
        try {
            //发起POST请求
            String jsonString = HttpClient.doPost(Url.ADMIN_SIGN_LOGIN_URL, jsonParam, null);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            log.info("登录账户" + jsonObject);
            //获取登录Token，用户修改密码之后，Token值会改变
            String authToken = jsonObject.getString("auth_token");
            company.setAuthToken(authToken);
            return company;

        } catch (Exception e) {
            log.error("登陆失败");
            return null;
        }
    }


    @Override
    public List<CompanyGroup> queryMemberGroup(String mid) throws Exception {
        // TODO Auto-generated method stub
        Company company = companyMapper.selectCompanyByMid(mid);
        System.out.println(company);
        String group = company.getGroup();
        List<CompanyGroup> companyGroup = JSONObject.parseArray(group, CompanyGroup.class);

        log.error("登录返回消息" + companyGroup);

        return companyGroup;
    }

    /**
     * @throws
     * @Title: insertMemberGroup
     * @Description: 添加会员组
     * @param: @param company
     * @param: @return
     * @param: @throws Exception
     * @return: Object
     */
    public Object insertMemberGroup(Company company) throws Exception {

        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer " + company.getAuthToken());

        String groupListJson = HttpClient.doGet(Url.COMPANY_CUSTOMER_GROUPS_URL, headers, null);

        JSONArray parseArray = JSONObject.parseArray(groupListJson);
        // TODO 插入一条记录
        log.error("登录返回消息" + parseArray);

        return parseArray;
    }

}
