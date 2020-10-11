package com.community.mapper;

import com.community.pojo.Notify;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NotifyMapper {
    Integer saveNotify(Notify notify);
    List<Notify> findNotifys(String userCode);
}
