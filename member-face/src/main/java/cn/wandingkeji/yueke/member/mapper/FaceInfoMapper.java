/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  FaceInfoMapper.java   
 * @Package cn.wandingkeji.yueke.member.mapper   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月21日 下午4:41:46   
 * @version V1.0 
 */
package cn.wandingkeji.yueke.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.member.model.FaceInfo;
import cn.wandingkeji.yueke.member.model.Member;
import org.springframework.stereotype.Repository;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  FaceInfoMapper   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月21日 下午4:41:46   
 */
@Repository
@Mapper
public interface FaceInfoMapper {

	/**   
	 * @Title: insert   
	 * @Description: 插入一条人脸信息
	 * @param: @param faceInfo      
	 * @return: void      
	 * @throws   
	 */
	Integer insert(FaceInfo faceInfo) throws Exception;

	/**   
	 * @Title: update   
	 * @Description: 修改人脸信息
	 * @param: @param faceInfo      
	 * @return: void      
	 * @throws   
	 */
	Integer update(FaceInfo faceInfo) throws Exception;

	/**
	 * @Title: selectMemberFaceInfo   
	 * @Description: 查询会员关联的人脸信息
	 * @param: @param id
	 * @param: @return      
	 * @return: FaceInfo      
	 * @throws   
	 */
	FaceInfo selectMemberFaceInfo(String id) throws Exception;

	/**   
	 * @Title: selectMemberByCustomerId   
	 * @Description: 查询会员根据customer_id
	 * @param: @param customer_id
	 * @param: @return      
	 * @return: Member      
	 * @throws   
	 */
	FaceInfo selectFaceInfoByCustomerId(String customerId) ;

}
