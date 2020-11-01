package com.community.mapper;

import com.community.pojo.User;
import jdk.nashorn.internal.ir.IdentNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int register(User user);
    int countUser(User user);
    User loginUser(User user);
    User findUpdateUser(User user);
    int updateUser(User user);
}
