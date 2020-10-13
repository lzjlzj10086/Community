package com.community.service;

import com.community.mapper.QuestionMapper;
import com.community.pojo.Comment;
import com.community.pojo.Notify;
import com.community.pojo.Question;
import com.community.util.CodeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotifyService notifyService;

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
        question.setStatus(2);
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

    /**
     * 相关问题查询
     * @param question
     * @return
     */
    public List<Question> aboutQuestion(Question question){
        String[] tags = question.getTags().split(",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        List<Question> aboutQuestions = questionMapper.aboutQuestions(question.getQuestionCode(),regexpTag);
        return aboutQuestions;
    }

    /**
     * 热门话题查询
     * @return
     */
    public List<Question> hotQuestion(){
        List<Question> hotQuestions = questionMapper.hotQuestions();
        return hotQuestions;
    }
    @Transactional
    public Integer updateQuestion(Question question){
        question.setUpdateTime(System.currentTimeMillis());
        return questionMapper.updateQuestion(question);
    }
    @Transactional
    public Integer saveComment(Comment comment,String questionUserCode){
        int saveComment = questionMapper.saveComment(comment);
        questionMapper.countCommentById(comment.getParentCode());
        if (comment.getParentType() == 2){
            questionMapper.countSubComments(comment.getParentCode());
        }
        //创建通知
        Notify notify = new Notify();
        notify.setCode(CodeUtil.NotifyCodeRandom());
        notify.setNotifyCode(comment.getUserCode());
        notify.setReceiverCode(questionUserCode);
        notify.setType(comment.getParentType());
        notify.setOuterCode(comment.getParentCode());
        notify.setStatus(1);
        notify.setCreateTime(System.currentTimeMillis());
        notify.setQuestionCode(comment.getQuestionCode());
        notifyService.saveNotify(notify);
        return saveComment;
    }

    public List<Comment> findComments(String questionCode){
        List<Comment> comments = questionMapper.findsComments(questionCode);
        return comments;
    }
    public List<Comment> subCommet(String parentCode){
        List<Comment> comments = questionMapper.subComments(parentCode);
        return comments;
    }
}
