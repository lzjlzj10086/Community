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

    /**
     * 查询我的所有
     * @param pageNum
     * @param pageSize
     * @param title
     * @param userCode
     * @return
     */
    public PageInfo<Question> findMyQuestion(Integer pageNum, Integer pageSize, String title,String userCode){
        PageHelper.startPage(pageNum,pageSize);
        List<Question> myQuestions = myMapper.findMyQuestions(title,userCode);
        PageInfo<Question> myQageInfo = new PageInfo<Question>(myQuestions);
        return myQageInfo;
    }

    /**
     * 根据状态查询
     * @param pageNum
     * @param pageSize
     * @param question
     * @return
     */
    public PageInfo<Question> findMyQuestionByStatus(Integer pageNum, Integer pageSize, Question question){
        PageHelper.startPage(pageNum,pageSize);
        List<Question> myQuestions = myMapper.findMyQuestionsByStatus(question);
        PageInfo<Question> myQageInfo = new PageInfo<Question>(myQuestions);
        return myQageInfo;
    }
    public Integer deleteQuestion(String questionCode){
        Integer deleteQuestion = myMapper.deleteQuestion(questionCode);
        return deleteQuestion;
    }
    public Integer countAll(Question question){
        return myMapper.countByAll(question);
    }
    public Integer countOne(Question question){
        return myMapper.countByOne(question);
    }
    public Integer counTwo(Question question){
        return myMapper.countByTwo(question);
    }
    public Integer countThere(Question question){
        return myMapper.countByThere(question);
    }
}
