package com.teapot.service.impl;

import com.teapot.dao.TbUserDao;
import com.teapot.pojo.TbUser;
import com.teapot.pojo.TbUserQuery;
import com.teapot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-3-28.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserDao userDao;
    public TbUser selectByUsername(String username) {
        TbUserQuery query = new TbUserQuery();
        TbUserQuery.Criteria criteria = query.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = userDao.selectByExample(query);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
}
