/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  MemberGroup.java   
 * @Package cn.wandingkeji.yueke.member.model   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午11:24:03   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.model;

import lombok.Data;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  MemberGroup   
 * @Description:会员组   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午11:24:03   
 */
@Data
public class MemberGroup {
	
	private String id;
	private String name;
	private String company_id;
	private String company_name;
	private String group_type;
	private String customer_count;
	private String created_at;
	private String updated_at;
	
}
