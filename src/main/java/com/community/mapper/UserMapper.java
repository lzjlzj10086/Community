package com.community.mapper;

import com.community.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int register(User user);
    int countUser(User user);
    User loginUser(User user);
}
