/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  MemeberManagerController.java   
 * @Package cn.wandingkeji.yueke.member.controller   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:05:08   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.wandingkeji.utils.constant.ConstantUtils;
import cn.wandingkeji.utils.http.ReturnData;
import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.model.CompanyGroup;
import cn.wandingkeji.yueke.company.service.CompanyService;
import cn.wandingkeji.yueke.member.model.Member;
import cn.wandingkeji.yueke.member.service.MemberManagerService;

import javax.annotation.Resource;

/**
 * 人脸会员注册
 * @author w.d
 *
 */
@RestController
@RequestMapping("/member")
public class MemberManagerController {
	
	private final static Logger logger = Logger.getLogger(MemberManagerController.class);
	
	@Resource
	private MemberManagerService memberManagerService;

	@Resource
	private CompanyService companyService;
	
	/**
	 * @Title: registered   
	 * @Description: 用户注册会员
	 * @param: @param file
	 * @param: @param member
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value = "/registered/face", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public ReturnData registered(@RequestParam("avatars[]") MultipartFile file, Member member, @RequestParam("mid") String mid) {
	
		logger.info("入参： " + member + ",商户ID:" + mid);
		Company company = companyService.login(mid);

		ReturnData memberJSON = null;
		try {
			//注册会员
			memberJSON = memberManagerService.registeredMember(member, company, file);
			
		} catch (Exception e) {

			logger.error(ExceptionUtils.getMessage(e));
			return ConstantUtils.printErrorMessage();
		}
	
		logger.info("出参： " + JSONObject.toJSONString(memberJSON));
		return memberJSON;
	}
	
	
	
	
	/**
	 * @Title: updateMember   
	 * @Description: 会员信息修改
	 * @param: @param file
	 * @param: @param member
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object updateMember(@RequestParam("avatars[]") MultipartFile file, Member member, @RequestParam("id") String mid) {
		
		Company admin = null;
		try {
			//登录
			admin = companyService.login(mid);
			logger.info("公司登录信息：" + admin);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getMessage(e));
			return ConstantUtils.printErrorMessage("登录失败");
		}
		
		Object memberJSON = null;
		try {
			//注册会员
			memberJSON = memberManagerService.updateMember(member, admin, file);
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getMessage(e));
			return ConstantUtils.printErrorMessage("登录失败");
		}
		
		return memberJSON;
	}
	
	@RequestMapping(value = "/group", produces = "application/json;charset=UTF-8")
	public Object memberGroup(@RequestParam("mid") String mid) {
		
		Company admin = null;
		try {
			//登录
			admin = companyService.login(mid);
			System.out.println(admin);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getMessage(e));
		}
		List<CompanyGroup> groupList = null;
		
		//查询会员组
		try {
				
			groupList = companyService.queryMemberGroup(admin.getMerchantId());
		} catch (Exception e) {
			logger.error(ExceptionUtils.getMessage(e));
		}
		
		return groupList;
	}
	
}
