package com.community.service;

import com.community.mapper.MyMapper;
import com.community.pojo.Question;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

    @Autowired
    private MyMapper myMapper;

    public PageInfo<Question> findMyQuestion(Integer pageNum, Integer pageSize, String title,String userCode){
        PageHelper.startPage(pageNum,pageSize);
        List<Question> myQuestions = myMapper.findMyQuestions(title,userCode);
        PageInfo<Question> myQageInfo = new PageInfo<Question>(myQuestions);
        return myQageInfo;
    }

    public Integer deleteQuestion(String questionCode){
        Integer deleteQuestion = myMapper.deleteQuestion(questionCode);
        return deleteQuestion;
    }
}
