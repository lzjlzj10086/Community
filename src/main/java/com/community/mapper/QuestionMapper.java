package com.community.mapper;

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
}
