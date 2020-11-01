package com.community.service;

import com.community.mapper.UserMapper;
import com.community.pojo.User;
import com.community.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoignService {
    @Autowired
    private UserMapper userMapper;

    public int register(User user){
        int count = userMapper.countUser(user);
        if (count != 0){
            return 0;
        }
        int register = userMapper.register(user);
        return register;
    }
    public User loginUser(User user){
        user.setPassWord(MD5Util.code(user.getPassWord()));
        User loginUser = userMapper.loginUser(user);
        return loginUser;
    }
    public User findUpdateUser(User user){
        User findUpdateUser = userMapper.findUpdateUser(user);
        return findUpdateUser;
    }
    public Integer updateUser(User user){
        User updateUser1 = userMapper.findUpdateUser(user);
        if (!updateUser1.getUserName().equals(user.getUserName())){
            int count = userMapper.countUser(user);
            if (count != 0){
                return 0;
            }
        }
        Integer updateUser = userMapper.updateUser(user);
        return updateUser;
    }
}
