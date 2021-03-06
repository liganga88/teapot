package com.teapot.service;

import com.teapot.pojo.TbWish;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */
public interface WishService {
    Integer addWish(String wish, String hoper, String tempId);
    TbWish selectById(Integer id);
    void updateSmsPhone(Integer id, String phone);
    List<TbWish> selectAll();
    List<TbWish> selectBySmsPhone(String phone);
    List<TbWish> selectBySmsPhoneBeforeDate(String phone, Date time);
    List<TbWish> selectByCustomerId(Integer customerId);
}
