/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  AdminServiceImpl.java   
 * @Package cn.wandingkeji.yueke.admin.service.impl   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:22:52   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wandingkeji.common.Url;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.wandingkeji.utils.constant.ConstantUtils;
import cn.wandingkeji.utils.http.HttpClient;
import cn.wandingkeji.yueke.company.mapper.CompanyMapper;
import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.model.CompanyGroup;
import cn.wandingkeji.yueke.company.service.CompanyService;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  AdminServiceImpl   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午9:22:52   
 */
@Service
public class CompanyServiceImpl implements CompanyService {
	
	private final static Logger logger = Logger.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyMapper companyMapper;


	@Override
	public Company login(String mid) throws Exception {
		Company company = companyMapper.selectCompanyByMid(mid);
		
		// 组织参数
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("admin", company);

		logger.info("compamy" +company);

		//发起POST请求
		String jsonString = HttpClient.doPost(Url.ADMIN_SIGN_LOGIN_URL , jsonParam, null);

		if(jsonString.isEmpty()){
			logger.error("yueku 登录失败");
			throw new Exception("doPost https://lightyear.readsense.cn:8080/v2/api/admins/sign_in error ");
		}

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        
        logger.info("登录账户" + jsonObject);

        //获取登录Token，用户修改密码之后，Token值会改变
        String auth_token = jsonObject.getString("auth_token");
        company.setAuthToken(auth_token);
       
		return company;
	}


	@Override
	public List<CompanyGroup> queryMemberGroup(String mid) throws Exception {
		// TODO Auto-generated method stub
		Company company = companyMapper.selectCompanyByMid(mid);
		System.out.println(company);
		String group = company.getGroup();
		List<CompanyGroup> companyGroup = JSONObject.parseArray(group, CompanyGroup.class);
		
		logger.error("登录返回消息" + companyGroup);
		
		return companyGroup;
	}
	
	/**
	 * @Title: insertMemberGroup   
	 * @Description: 添加会员组
	 * @param: @param company
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: Object      
	 * @throws
	 */
	public Object insertMemberGroup(Company company) throws Exception {
		
		Map<String, String> headers = new HashMap<>();
				
		headers.put("Authorization", "Bearer " + company.getAuthToken());
		
		String groupListJson = HttpClient.doGet(Url.COMPANY_CUSTOMER_GROUPS_URL, headers, null);
		
		JSONArray parseArray = JSONObject.parseArray(groupListJson);
		// TODO 插入一条记录
		logger.error("登录返回消息" + parseArray);
		
		return parseArray;
	}

}
