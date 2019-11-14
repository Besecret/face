/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  Admin.java   
 * @Package cn.wandingkeji.yueke.member.domain   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:09:19   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  Admin   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午9:09:19   
 */
@Data
@ToString
public class Company {
	
	private String id;

	private String phone;
	
	private String password;
	
	private String group;
	
	private String merchantId;
	
	private String authToken;

}
