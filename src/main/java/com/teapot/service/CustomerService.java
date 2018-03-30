package com.teapot.service;

import com.teapot.pojo.TbCustomer;

/**
 * Created by Administrator on 2018-3-30.
 */
public interface CustomerService {
    TbCustomer selectByPhone(String phone);
    void newByPhone(String phone, String tempId);
}
