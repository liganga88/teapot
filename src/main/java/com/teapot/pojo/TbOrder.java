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
     * 金额
     */
    private Integer money;

    /**
     * 发送的手机号
     */
    private String sendphone;

    /**
     * 创建时间
     */
    private Date created;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerid=").append(customerid);
        sb.append(", money=").append(money);
        sb.append(", sendphone=").append(sendphone);
        sb.append(", created=").append(created);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}