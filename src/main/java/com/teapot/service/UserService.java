package com.teapot.service;

import com.teapot.pojo.TbUser;

/**
 * Created by Administrator on 2018-3-28.
 */
public interface UserService {
    TbUser selectByUsername(String username);
}
