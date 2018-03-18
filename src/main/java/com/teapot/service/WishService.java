package com.teapot.service;

import com.teapot.pojo.TbWish;

/**
 * Created by Administrator on 2018/3/17.
 */
public interface WishService {
    Integer addWish(String wish, String hoper, String tempId);
    TbWish selectById(Integer id);
}
