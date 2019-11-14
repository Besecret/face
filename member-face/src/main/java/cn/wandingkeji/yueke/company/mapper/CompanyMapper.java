/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  CompanyMapper.java   
 * @Package cn.wandingkeji.yueke.company.mapper   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月26日 下午5:55:54   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.company.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.company.model.Company;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  CompanyMapper   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月26日 下午5:55:54   
 */
@Mapper()
public interface CompanyMapper {

	/**   
	 * @Title: selectCompanyByMid   
	 * @Description: 查询公司根据商户id
	 * @param: @param mid
	 * @param: @return      
	 * @return: Company      
	 * @throws   
	 */
	Company selectCompanyByMid(String mid) throws Exception;

}
