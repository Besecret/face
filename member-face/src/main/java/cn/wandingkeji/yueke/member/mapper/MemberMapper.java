
package cn.wandingkeji.yueke.member.mapper;

import cn.wandingkeji.yueke.member.model.MemberMesTemplate;
import org.apache.ibatis.annotations.Mapper;

import cn.wandingkeji.yueke.member.model.Member;
import org.springframework.stereotype.Repository;

/**
 * 会员mapper
 * @author w.d.k.j
 */
@Mapper
@Repository
public interface MemberMapper {

	/**
	 *	主键查询
	 * @param id 主键
	 * @return 会员
	 */
	Member selectMemberById(int id);

	/**
	 *  customer id 查询会员
	 * @param customerId  customer id
	 * @return 会员
	 */
	MemberMesTemplate selectMemberByCustomerId(String customerId);



}
