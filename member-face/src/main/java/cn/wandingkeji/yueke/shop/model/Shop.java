/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  Shop.java   
 * @Package cn.wandingkeji.yueke.shop.model   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 下午6:08:59   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.shop.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  Shop   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月24日 下午6:08:59   
 */
@Data
@ToString
public class Shop {
	
	private String id;
	private String shopId;
	private String storeId;
	private String merchantId;
	private String companyId;
	private String merchantTokenId;
}
