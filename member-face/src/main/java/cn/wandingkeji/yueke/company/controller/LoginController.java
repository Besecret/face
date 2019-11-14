/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  LoginController.java   
 * @Package cn.wandingkeji.yueke.member.controller   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:01:28   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.service.CompanyService;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  LoginController   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午9:01:28   
 */
//@RestController
//@RequestMapping("")
public class LoginController {
	
	//@Autowired
	private CompanyService adminService;
	
	
	//@RequestMapping("/login")
	public String login(@RequestBody Company admin) {
	
		
		
		return null;
	}
	

}
