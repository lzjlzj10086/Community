package com.community.mapper;

import com.community.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MyMapper {
    /**
     * 查询我的全部
     * @param title
     * @param userCode
     * @return
     */
    List<Question> findMyQuestions(String title,String userCode);

    /**
     * 根据状态查询
     * @param question
     * @return
     */
    List<Question> findMyQuestionsByStatus(Question question);
    Integer countByAll(Question question);
    Integer countByOne(Question question);
    Integer countByTwo(Question question);
    Integer countByThere(Question question);
    Integer deleteQuestion(String questionCode);
}
