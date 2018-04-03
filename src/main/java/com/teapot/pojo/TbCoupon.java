package com.teapot.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbCoupon implements Serializable {
    private Integer id;

    /**
     * 优惠券模版ID
     */
    private Integer templateid;

    /**
     * 客户ID
     */
    private Integer customerid;

    /**
     * 状态(0:未使用 1:已使用)
     */
    private Byte state;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 验证码
     */
    private String token;

    /**
     * 使用时间
     */
    private Date usedtime;

    /**
     * 产生优惠券的订单ID
     */
    private Integer orderid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getUsedtime() {
        return usedtime;
    }

    public void setUsedtime(Date usedtime) {
        this.usedtime = usedtime;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", templateid=").append(templateid);
        sb.append(", customerid=").append(customerid);
        sb.append(", state=").append(state);
        sb.append(", created=").append(created);
        sb.append(", token=").append(token);
        sb.append(", usedtime=").append(usedtime);
        sb.append(", orderid=").append(orderid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}