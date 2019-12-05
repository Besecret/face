/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  AdminService.java   
 * @Package cn.wandingkeji.yueke.member.service   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:20:54   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.service;

import java.util.List;

import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.company.model.CompanyGroup;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  AdminService   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午9:20:54   
 */
public interface CompanyService {

	/**   
	 * @Title: login   
	 * @Description: 登录
	 * @param: @param adminLoginPhone
	 * @param: @param adminLoginPassword
	 * @param: @return      
	 * @return: Admin      
	 * @throws   
	 */
	Company login(String mid);

	/**   
	 * @Title: queryMemberGroup   
	 * @Description: 查询会员组
	 * @param: @param admin
	 * @param: @return      
	 * @return: List<MemberGroup>      
	 * @throws   
	 */
	List<CompanyGroup> queryMemberGroup(String merchartId) throws Exception;

}
