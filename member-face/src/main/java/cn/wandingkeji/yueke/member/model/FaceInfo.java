
package cn.wandingkeji.yueke.member.model;

import lombok.Data;
import lombok.ToString;

/**
 * 人脸信息 vo
 * @author  w.d.k.j
 */
@Data
@ToString
public class FaceInfo {


	/**
	 * id
	 */
	private String id;


	/**
	 * person id
	 */
	private String personId;


	/**
	 *  顾客id
	 */
	private String customerId;


	/**
	 * 人脸地址
	 */
	private String faceUrl;

	/**
	 * opend id
	 */
	private String openId;


	/**
	 * 组id
	 */
	private String groupId;

	/**
	 * 名称
	 */
	private  String  name;

	/**
	 * 手机号
	 */
	private  String	 phone;

	/**
	 * 性别
	 */
	private  Integer gender;

	/**
	 * 生日
	 */
	private  String	 birthday;

}
