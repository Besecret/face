/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  Member.java   
 * @Package cn.wandingkeji.yueke.member.domain   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 上午9:27:38   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  Member   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 上午9:27:38   
 */
@Data
@ToString
public class Member {
	
	private String id;
	private String personId;
	private String memberGroupId;
	private String phone;
	private String name;
	private String birthday;
	private String gender;
	private String mid;
	private String cardId;
	private String cardNo;
	private String cardBarcode;
	private String bonus;
	private String balance;
	private String allPaymoney;
	private String type;
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
