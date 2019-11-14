/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  Employee.java   
 * @Package cn.wandingkeji.yueke.employee.model   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 下午6:05:49   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.employee.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  Employee   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月24日 下午6:05:49   
 */
@Data
@ToString
public class Employee {
	
	private String id;
	private String shopId;
	private String customerId;
	private String openId;
	
}
