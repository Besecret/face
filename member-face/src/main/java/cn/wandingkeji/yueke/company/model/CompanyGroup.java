/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  CompanyGroup.java   
 * @Package cn.wandingkeji.yueke.company.model   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月26日 下午8:24:59   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  CompanyGroup   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月26日 下午8:24:59   
 */
@Data
@ToString
public class CompanyGroup {
	
	private String id;
	private String name;
	private String groupId;
	private String companyId;
	private String merchanrtId;
	private String groupType;
}
