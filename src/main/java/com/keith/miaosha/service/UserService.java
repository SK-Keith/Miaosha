package com.keith.miaosha.service;

import com.keith.miaosha.dao.UserDao;
import com.keith.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author YMX
 * @date 2018/12/29 11:46
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getUserById(id);
    }


}
