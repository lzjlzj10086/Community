package com.community.mapper;

import com.community.pojo.Comment;
import com.community.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    int saveQuestion(Question question);
    List<Question> findQuestions(String title);
    Question findQuestionById(String questionCode);
    void countViewById(String questionCode);
    Integer updateQuestion(Question question);
    int saveComment(Comment comment);
    List<Comment> findsComments(String questionCode);
    List<Comment> subComments(String parentCode);
    void countCommentById(String questionCode);
    void countSubComments(String parentCode);

    /**
     * 相关问题集合查询
     * @param question
     * @return
     */
    List<Question> aboutQuestions(Question question);
}
