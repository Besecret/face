package cn.wandingkeji.common;

/**
 * 通用地址
 * @author w.d.k.j
 */
public class Url {

    /**
     * 约客 API
     */
    private final static String YUEKE = "https://lightyear.readsense.cn:8080/v2/";

    /**
     * admin 登陆接口
     */
    public final static String ADMIN_SIGN_LOGIN_URL = YUEKE + "api/admins/sign_in";

    /**
     * 获取会员组接口
     */
    public final static String COMPANY_CUSTOMER_GROUPS_URL = YUEKE + "api/company/customer_groups";

    /**
     * 创建会员组
     */
    public final static String COMPANY_CUSTOMERS_URL = YUEKE + "api/company/customers";

    /**
     * 微信发送模板请求
     */
    public final static String WX_SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     *测试获取Token地址
     */
    public final static String WX_ACCESS_TOKEN_URL = "http://10.0.0.5:9000/member-token/access_token/%s";

    /**
     *图片临时上传地址，注册约科使用
     */
    public final static String IMG_PATH = "/data/wwwroot/default/";

    /**
     *会员组 url
     */
    public final static String CUSTOMER_GROUPS_URL = YUEKE + "api/company/customer_groups";


}
