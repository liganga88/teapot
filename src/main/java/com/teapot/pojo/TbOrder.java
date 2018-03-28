package com.teapot.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbOrder implements Serializable {
    private Integer id;

    /**
     * 客户ID
     */
    private Integer customerid;

    /**
     * 客户临时编号
     */
    private String tempid;

    /**
     * 金额
     */
    private Integer money;

    /**
     * 发送的手机号
     */
    private String sendphone;

    /**
     * 状态 0:待支付 1:已支付
     */
    private Byte state;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 支付类型 1:支付宝支付 2:微信支付
     */
    private Byte paytype;

    /**
     * 支付宝单号或微信支付单号
     */
    private String payno;

    /**
     * 支付时间
     */
    private Date paytime;

    /**
     * 祈愿人
     */
    private String hoper;

    /**
     * 祈福单ID
     */
    private Integer wishid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getSendphone() {
        return sendphone;
    }

    public void setSendphone(String sendphone) {
        this.sendphone = sendphone == null ? null : sendphone.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Byte getPaytype() {
        return paytype;
    }

    public void setPaytype(Byte paytype) {
        this.paytype = paytype;
    }

    public String getPayno() {
        return payno;
    }

    public void setPayno(String payno) {
        this.payno = payno == null ? null : payno.trim();
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public String getHoper() {
        return hoper;
    }

    public void setHoper(String hoper) {
        this.hoper = hoper == null ? null : hoper.trim();
    }

    public Integer getWishid() {
        return wishid;
    }

    public void setWishid(Integer wishid) {
        this.wishid = wishid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerid=").append(customerid);
        sb.append(", tempid=").append(tempid);
        sb.append(", money=").append(money);
        sb.append(", sendphone=").append(sendphone);
        sb.append(", state=").append(state);
        sb.append(", created=").append(created);
        sb.append(", paytype=").append(paytype);
        sb.append(", payno=").append(payno);
        sb.append(", paytime=").append(paytime);
        sb.append(", hoper=").append(hoper);
        sb.append(", wishid=").append(wishid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}