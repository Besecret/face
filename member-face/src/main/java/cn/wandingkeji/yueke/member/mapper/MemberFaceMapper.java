/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  MemberFace.java   
 * @Package cn.wandingkeji.yueke.member.mapper   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 下午4:42:33   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.member.model.MemberAndFace;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  MemberFace   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 下午4:42:33   
 */
@Mapper
public interface MemberFaceMapper {

	/**   
	 * @Title: insert   
	 * @Description: 添加会员信息与人脸信息
	 * @param: @param memberAndFace      
	 * @return: void      
	 * @throws   
	 */
	Integer insert(MemberAndFace memberAndFace) throws Exception;

	/**   
	 * @Title: selectMemberCount   
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return      
	 * @return: Integer      
	 * @throws   
	 */
	Integer selectMemberCount(String id) throws Exception;

}
