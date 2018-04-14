package com.teapot.dto;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/14.
 */
public class WishDto {
    private Integer id;

    private Integer customerid;

    /**
     * 临时用户ID
     */
    private String tempid;

    /**
     * 祈福内容
     */
    private String wish;

    /**
     * 祈愿人
     */
    private String hoper;

    /**
     * 短信发送的电话号码
     */
    private String smsphone;

    /**
     * 创建时间
     */
    private Date created;

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
        this.tempid = tempid;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getHoper() {
        return hoper;
    }

    public void setHoper(String hoper) {
        this.hoper = hoper;
    }

    public String getSmsphone() {
        return smsphone;
    }

    public void setSmsphone(String smsphone) {
        this.smsphone = smsphone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
