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
import org.springframework.stereotype.Repository;

/**
 *
 * 查找公司
 * @author  w.d.k.j
 *
 */
@Mapper
@Repository
public interface CompanyMapper {

	/**
	 * 查询公司 通过id
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	Company selectCompanyByMid(String mid);

}
