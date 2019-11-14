/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  MemberManagerService.java   
 * @Package cn.wandingkeji.yueke.member.service   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:12:26   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.service;

import org.springframework.web.multipart.MultipartFile;

import cn.wandingkeji.utils.http.ReturnData;
import cn.wandingkeji.yueke.company.model.Company;
import cn.wandingkeji.yueke.member.model.Member;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  MemberManagerService   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午9:12:26   
 */
public interface MemberManagerService {

	/**   
	 * @Title: registeredMember   
	 * @Description: 注册会员
	 * @param: @param member      
	 * @return: Member      
	 * @throws   
	 */
	ReturnData<Member> registeredMember(Member member, Company company, MultipartFile file) throws Exception;

	/**   
	 * @Title: updateMember   
	 * @Description: 修改会员
	 * @param: @param member
	 * @param: @param admin
	 * @param: @param file
	 * @param: @return      
	 * @return: Object      
	 * @throws   
	 */
	Object updateMember(Member member, Company company, MultipartFile file) throws Exception;



}
