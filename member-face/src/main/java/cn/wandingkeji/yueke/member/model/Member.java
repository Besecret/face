
package cn.wandingkeji.yueke.member.model;

import lombok.Data;
import lombok.ToString;

/**
 * 会员注册 member
 * @author w.d.k.j
 */
@Data
@ToString
public class Member {
	
	private Integer id;


	private String personId;

	private String memberGroupId;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 员工类型
	 */
	private Integer type;



	private String mid;

	private String cardId;
	private String cardNo;
	private String cardBarcode;
	private String bonus;
	private String balance;
	private String allPaymoney;

	private String wxName;
	private String wxPic;
	private String sex;
	private String email;
	private String address;
	private String addtime;
	private String expensesTotal;
	private String education;
	private String industry;
	private String year;
	private String month;
	private String day;
	private String salary;
	private String likes;
	private String gradeId;
	private String gradeName;
	private String msgNub;
	private String openid;
	private String storeId;
	private String storeName;
	private String provinceCode;
	private String cityCode;
	private String updatePaytime;
	private String payNub;
	private String tags;
	private String pw;
	private String status;
	private String outerid;
	private String idcard;
	private String hasActive;
	private String level;
	private String levelName;
	private String creatTime;
	private String salt;
	private String locStatus;
	private String miniAppid;
	private String miniOpenid;

}
