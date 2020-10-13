package com.community.service;

import com.community.mapper.AdminMapper;
import com.community.pojo.Question;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public PageInfo<Question> findQuestionsByAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionsByAdmin = adminMapper.findQuestionsByAdmin();
        PageInfo<Question> pageInfo = new PageInfo<Question>(questionsByAdmin);
        return pageInfo;
    }
}
