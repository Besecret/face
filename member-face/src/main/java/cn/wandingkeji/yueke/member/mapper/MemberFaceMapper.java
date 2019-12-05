
package cn.wandingkeji.yueke.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.member.model.MemberAndFace;
import org.springframework.stereotype.Repository;

/**
 * 会员人脸 mapper
 * @author  w.d.k.j
 */
@Mapper
@Repository
public interface MemberFaceMapper {

	/**
	 * 新增人脸信息
	 * @param memberAndFace 会员人脸信息
	 * @return 数据库影响的行
	 */
	Integer insert(MemberAndFace memberAndFace);

	/**
	 * 通过主键统计会员
	 * @param id 主键
	 * @return 个数
	 */
	Integer countMemberById(Integer id);

}
