package com.community.service;

import com.community.mapper.NotifyMapper;
import com.community.pojo.Notify;
import com.community.pojo.Question;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyService {
    @Autowired
    private NotifyMapper notifyMapper;
    public void saveNotify(Notify notify){
        notifyMapper.saveNotify(notify);
    }
    public PageInfo<Notify> findNotifys(String userCode, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Notify> notifyList = notifyMapper.findNotifys(userCode);
        PageInfo<Notify> pageInfo = new PageInfo<Notify>(notifyList);
        return pageInfo;
    }
}
