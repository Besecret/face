/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  ShopMapper.java   
 * @Package cn.wandingkeji.yueke.employee.mapper   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 下午6:06:49   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.shop.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.shop.model.Shop;
import org.springframework.stereotype.Repository;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  ShopMapper   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月24日 下午6:06:49   
 */
@Mapper
@Repository
public interface ShopMapper {

	/**   
	 * @Title: selectShop   
	 * @Description: 查询商店
	 * @param: @param shop_id
	 * @param: @return      
	 * @return: Shop      
	 * @throws   
	 */
	Shop selectShopByShopId(String shop_id);


	/**
	 * 获取门店名称
	 * @param id
	 * @return mname
	 */
	String selectStoreNameByStoreId(int id);
}
