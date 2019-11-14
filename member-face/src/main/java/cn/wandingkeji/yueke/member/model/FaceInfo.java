/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  FaceInfo.java   
 * @Package cn.wandingkeji.yueke.member.model   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 上午9:36:11   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  FaceInfo   
 * @Description:人脸信息  
 * @author: 薛展峰
 * @date:   2019年6月24日 上午9:36:11   
 */
@Data
@ToString
public class FaceInfo {
	
	private String id;
	private String personId;
	private String customerId;
	private String memberGroupId;
	private String faceUrl;
	private String openId;
	private String groupId;
}
