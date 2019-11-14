/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  MerchantsTemplate.java   
 * @Package cn.wandingkeji.yueke.company.model   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月26日 下午5:27:39   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  MerchantsTemplate   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月26日 下午5:27:39   
 */
@Data
@ToString
public class MerchantToken {
	
	private String id;
	private String merchantsId;
	private String tokenId;
	private String tokenType;
	private String createTime;

}
