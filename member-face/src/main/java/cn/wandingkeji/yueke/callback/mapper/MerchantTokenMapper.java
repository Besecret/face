package cn.wandingkeji.yueke.callback.mapper;

import cn.wandingkeji.yueke.company.model.MerchantToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商户token mapper
 *
 * @author w.d.k.j
 */

@Mapper
@Repository
public interface MerchantTokenMapper {


    /**
     * 获取商户token
     *
     * @param mid  mid
     * @param type 类型
     * @return token
     */
    MerchantToken selectMerchantToken(@Param("mid") String mid, @Param("type") String type);

}
