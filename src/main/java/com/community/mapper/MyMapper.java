package com.community.mapper;

import com.community.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MyMapper {
    List<Question> findMyQuestions(String title,String userCode);
    Integer deleteQuestion(String questionCode);
}
