package com.teapot.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbWish implements Serializable {
    private Integer id;

    private Integer customerid;

    /**
     * ��ʱ�û�ID
     */
    private String tempid;

    /**
     * ������
     */
    private String wish;

    /**
     * ��Ը��
     */
    private String hoper;

    /**
     * ����ʱ��
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

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish == null ? null : wish.trim();
    }

    public String getHoper() {
        return hoper;
    }

    public void setHoper(String hoper) {
        this.hoper = hoper == null ? null : hoper.trim();
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
        sb.append(", tempid=").append(tempid);
        sb.append(", wish=").append(wish);
        sb.append(", hoper=").append(hoper);
        sb.append(", created=").append(created);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}