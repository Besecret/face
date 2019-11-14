/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  ShopController.java   
 * @Package cn.wandingkeji.yueke.shop.controller   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 下午6:26:48   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  ShopController   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月24日 下午6:26:48   
 */
@RestController
@RequestMapping("/shop")
public class ShopController {

	//@Autowired
	//private ShopSerivce shopService;
	
	
	@RequestMapping("/add")
	public Object shop() {
		
		
		//shopService.addShop();
		return null;
	}
	
	
	
}
