package cn.wandingkeji.yueke.callback.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;


/**
 * 事件model -> 对接约科
 *
 * @author w.d.k.j
 */

@Data
@ToString
public class EventModel {


    /**
     * event id  主键
     */
    private Integer id;

    /**
     * customer_id 顾客ID
     */
    @JSONField(name = "customer_id")
    private Integer customerId;

    /**
     * person_id 人物主键
     */
    @JSONField(name = "person_id")
    private String personId;


    /**
     * face_id 人脸id
     */
    @JSONField(name = "face_id")
    private String faceId;

    /**
     * device_id 设备id
     */
    @JSONField(name = "device_id")
    private Integer deviceId;

    /**
     * shop_id 店铺id
     */
    @JSONField(name = "shop_id")
    private Integer shopId;

    /**
     * shop_name 店铺名字
     */
    @JSONField(name = "shop_name")
    private String shopName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 物理设备地址
     */
    @JSONField(name = "device_mac_address")
    private String deviceMacAddres;

    /**
     * 人脸保存地址
     */
    @JSONField(name = "original_face")
    private String originalFace;

    /**
     * 顾客信息
     */
    private Customer customer;


    /**
     * 拍摄时间
     */

    @JSONField(name = "capture_at",format="yyyy-MM-dd HH:mm:ss")
    private Date captureTime;

}
