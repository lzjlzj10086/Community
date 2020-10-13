package com.community.mapper;

import com.community.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface AdminMapper {
    List<Question> findQuestionsByAdmin();
    List<Question> findQuestionsByAdminOfNotPass();
    Integer updateQuestionStatusById(Question question);
}
