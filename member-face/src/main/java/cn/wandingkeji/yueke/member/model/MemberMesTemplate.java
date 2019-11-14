package cn.wandingkeji.yueke.member.model;

import java.sql.Timestamp;

/**
 * 会员发送消息模板 ；微信推送使用
 * @author w.d.k.j
 */
public class MemberMesTemplate {

    /**
     * opeid 微信openid
     */
    private String openId;

    /**
     * wx 官方模板
     */
    private String templateId;

    //定制字段
    /**
     * 跳转url
     */
    private String url;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     *  消费金额
     */
    private String balance;

    /**
     * 最近一次消费门店id
     */
    private String storeIdLatest;

    /**
     * 最近一次消费门店名称
     */
    private String storeNameLatest;

    /**
     * 上次消费时间
     */
    private Timestamp payTimeLatest;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 等级id
     */
    private String levelId;

    /**
     * 等级
     */
    private String levelName;

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setStoreIdLatest(String storeIdLatest) {
        this.storeIdLatest = storeIdLatest;
    }

    public void setStoreNameLatest(String storeNameLatest) {
        this.storeNameLatest = storeNameLatest;
    }

    public void setPayTimeLatest(Timestamp payTimeLatest) {
        this.payTimeLatest = payTimeLatest;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }


    public String getOpenId() {
        return this.openId;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public String getUrl() {
        return this.url;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public String getBalance() {
        return this.balance;
    }

    public String getStoreIdLatest() {
        return this.storeIdLatest;
    }

    public String getStoreNameLatest() {
        return this.storeNameLatest;
    }

    public Timestamp getPayTimeLatest() {
        return this.payTimeLatest;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getLevelId() {
        return this.levelId;
    }

    public String getLevelName() {
        return this.levelName;
    }
}
