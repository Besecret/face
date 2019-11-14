/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  EmployeeMapper.java   
 * @Package cn.wandingkeji.yueke.employee.mapper   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 下午6:07:25   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.employee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.employee.model.Employee;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  EmployeeMapper   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月24日 下午6:07:25   
 */
@Mapper
public interface EmployeeMapper {

	/**   
	 * @Title: selectEmployeeList   
	 * @Description: 查询雇员列表
	 * @param: @param shop_id
	 * @param: @return      
	 * @return: List<Employee>      
	 * @throws   
	 */
	List<Employee> selectEmployeeList(String shop_id) throws Exception;

	/**
	 * 添加雇员
	 * @param employee
	 * @return
	 */
	Integer insert(Employee employee);

	/**
	 * 查询雇员
	 * @param employee
	 * @return
	 */
	Employee selectEmp(String openID);

}
