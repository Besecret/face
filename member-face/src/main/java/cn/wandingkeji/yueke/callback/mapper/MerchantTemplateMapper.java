package cn.wandingkeji.yueke.callback.mapper;

import cn.wandingkeji.yueke.callback.model.MerchantTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商户模板 mapepr
 *
 * @author w.d.k.j
 */

@Mapper
@Repository
public interface MerchantTemplateMapper {

    /**
     * 获取微信推送模板
     *
     * @param companyId 商户id
     * @return 商户模板
     */
    List<MerchantTemplate> selectMerchantTemplateByCompanyId(String companyId);

}
