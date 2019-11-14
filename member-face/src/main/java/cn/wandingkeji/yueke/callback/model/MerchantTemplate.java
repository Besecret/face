/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  CompanyTemplate.java   
 * @Package cn.wandingkeji.yueke.callback.model
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月26日 上午11:55:37   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.callback.model;

import lombok.Data;
import lombok.ToString;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  CompanyTemplate   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月26日 上午11:55:37   
 */
@Data
@ToString
public class MerchantTemplate {
	
	private String id;
	private String companyId;
	private String templateId;
	private String thirdTemplateId;
	private String type;
	
	private Template template;

}
