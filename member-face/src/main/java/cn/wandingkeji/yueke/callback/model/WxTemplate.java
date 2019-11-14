/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  Template.java   
 * @Package cn.wandingkeji.yueke.callback.model
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 下午8:07:35   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.callback.model;

import java.util.Map;

import lombok.Data;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  Template   
 * @Description: 微信模板  
 * @author: 薛展峰
 * @date:   2019年6月24日 下午8:07:35   
 */
@Data
public class WxTemplate {

	private String touser;
    private String template_id;
    private String url;
    private Miniprogram minipeogram;
    private Map<String, TemplateData> data;
    
    @Data
    public class Miniprogram {
    	private String appid;
    	private String pagepath;
    }
    
    
    
	
}
