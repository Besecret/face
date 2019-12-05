
package cn.wandingkeji.yueke.company.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 *  公司组vo
 * @author  w.d.k.j
 */
@Data
@ToString
public class CompanyGroup {

	/**
	 * 组id
	 */
	private String id;

	/**
	 * 组名称
	 */
	private String name;

	/**
	 * 公司id
	 */

	@JSONField(name = "company_id")
	private Integer companyId;

	/**
	 * 公司id
	 */

	@JSONField(name = "company_name")
	private String companyName;

	/**
	 * 商户id
	 */
	private String merchanrtId;


	/**
	 * 组类型
	 */
	@JSONField(name = "group_type")
	private String groupType;


	/**
	 *  组内员工个数
	 */
	@JSONField(name = "customer_count")
	private Integer customerCount;

	/**
	 * 公司组id 注册公司时id
	 */

	@JSONField(name = "company_group_id")
	private Integer companyGroupId;

}
