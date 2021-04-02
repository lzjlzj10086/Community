package com.community.teststudy.mapper;



import com.community.teststudy.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface QuestionTestStudyMapper {
	/**
	 * 添加一个测试题
	 * @param question
	 */
	@Insert("insert into question(subject,item1,item2,item3,item4,answer,usercode,createtime)"
			+ " value(#{subject},"
			+ "#{item1},#{item2},#{item3},#{item4},#{answer},#{usercode},#{createTime})")
	void add(Question question);
	
	/**
	 * 分页查找
	 * @param size  数据的大小 (数量)
 	 * @param start  起始位置
	 * @return
	 */
	@Select("select id,subject,item1,item2,item3,item4 from question where usercode = #{usercode} order by createtime desc limit #{size} offset #{start}")
	List<Question> list(
			@Param("size")int size,
			@Param("start")int start,String usercode);
	@Select("select * from question where usercode = #{usercode} order by createtime desc")
	List<Question> ans(String usercode);
	
}