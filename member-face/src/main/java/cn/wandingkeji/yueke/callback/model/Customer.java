package cn.wandingkeji.yueke.callback.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * 顾客model -> 对接约客平台
 */

@Data
@ToString
public class Customer {


    /**
     * id -> customerID
     */
    private Integer id;

    /**
     * 姓名(注册时填写姓名)
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * person id 约客使用人脸
     */
    @JSONField(name = "person_id")
    private String personId;


    /**
     * 上次拍摄时间
     */
    @JSONField(name = "last_capture_at")
    private String lastCaptureTime;

    /**
     * 拍摄时间
     */
    @JSONField(name = "capture_at")
    private String captureTime;


    /**
     * 会员标识
     */
    @JSONField(name = "is_vip")
    private Boolean isVip;

}
