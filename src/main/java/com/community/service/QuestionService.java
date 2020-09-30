package com.community.service;

import com.community.mapper.QuestionMapper;
import com.community.pojo.Question;
import com.community.util.CodeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 发布话题
     * @param question
     * @return
     */
    public int saveQuestion(Question question){
        question.setQuestionCode(CodeUtil.QuestionCodeRandom());
        question.setCreateTime(System.currentTimeMillis());
        question.setUpdateTime(System.currentTimeMillis());
        question.setViewCount(0);
        question.setCommentCount(0);
        question.setLikeCount(0);
        int saveQuestion = questionMapper.saveQuestion(question);
        return saveQuestion;
    }

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Question> findQuestion(Integer pageNum,Integer pageSize,String title){
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = questionMapper.findQuestions(title);
        PageInfo<Question> pageInfo = new PageInfo<Question>(questions);
        return pageInfo;
    }

    /**
     * 查询详情
     * @param questionCode
     * @return
     */
    public Question findQuestionById(String questionCode){
        Question question = questionMapper.findQuestionById(questionCode);
        questionMapper.countViewById(questionCode);
        return question;
    }
    @Transactional
    public Integer updateQuestion(Question question){
        question.setUpdateTime(System.currentTimeMillis());
        return questionMapper.updateQuestion(question);
    }
}
