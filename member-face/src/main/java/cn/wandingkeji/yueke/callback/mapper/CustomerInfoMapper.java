package cn.wandingkeji.yueke.callback.mapper;


import cn.wandingkeji.yueke.callback.model.EventModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 顾客信息mapper
 * @author w.d.k.j
 */

@Mapper
@Repository
public interface CustomerInfoMapper {



    EventModel queryCustomerByCustomerId(@Param("event") EventModel event);
    /**
     * 保存顾客信息
     * @param event 事件event
     * @return num
     */
    int saveCustomerInfo(@Param("event") EventModel event);

    /**
     * 保存顾客信息
     * @param event 事件event
     * @return num
     */
    int updateCustomerInfoById(@Param("event") EventModel event);

}
