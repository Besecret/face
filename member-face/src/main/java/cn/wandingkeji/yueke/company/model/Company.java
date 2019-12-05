
package cn.wandingkeji.yueke.company.model;

import lombok.Data;
import lombok.ToString;

/**
 * 公司&管理员 vo
 * @author w.d.k.j
 */
@Data
@ToString
public class Company {

	/**
	 * 主键
	 */
	private String id;

	/**
	 *  登陆电话
	 */
	private String phone;

	/**
	 * 登陆密码
	 */
	private String password;

	/**
	 * 公司组
	 */
	private String group;

	/**
	 * 商户id
	 */
	private String merchantId;


	/**
	 * token
	 */
	private String authToken;

}
