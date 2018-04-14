package com.teapot.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018-3-30.
 */
public class OrderDto implements Serializable {
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

    /**
     * 排名
     */
    private Integer rank;

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getXhoper() {
        int len = hoper.length();
        if (StringUtils.isBlank(hoper)) {
            return "";
        } else {
            StringBuilder s = new StringBuilder();
            if (len > 1) {
                for (int i = 0; i < len - 1; i++) {
                    s.append("*");
                }
            }
            return hoper.substring(0, 1) + s.toString();
        }
    }
}
