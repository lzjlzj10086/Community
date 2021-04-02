package com.community.teststudy.controller;


import com.community.pojo.User;
import com.community.teststudy.mapper.QuestionTestStudyMapper;
import com.community.teststudy.pojo.Answer;
import com.community.teststudy.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionTestStudyController {
	/**
	 * 自动装配（）
	 */
	@Autowired
	QuestionTestStudyMapper mapper;
	double a;
//	public String foo(
//			HttpServletRequest request,
//			HttpServletResponse response,
//			HttpSession session,
//			ServletContext application
//			) {
//		String subject=request.getParameter("subject");
//		
//		return "";
//	}
	
//	@PostMapping("/answers")
//	public Map<String,Object> submitAnswer(
//			@RequestBody List<Answer> answers
//			){
//		for (Answer answer : answers) {
//			System.out.println(answer);
//		}
//		
//		Map<String,Object>data =new HashMap<>();
//		return data;
//	}
	
	@PostMapping
	public Map<String, Object> create (@RequestBody Question question,HttpSession session){
		User user = (User) session.getAttribute("user");
		question.setUsercode(user.getUserCode());
		question.setCreateTime(System.currentTimeMillis());
		System.out.println("QuestionController: "+question);
		//在控制器中调用Mapper中的方法
		mapper.add(question);
		Map<String, Object> msg=new HashMap<>();
		msg.put("msg", "ok");
		return msg;
	}
	
	@PostMapping("/submit")
	public double send(@RequestBody List<Answer>answers){
		for (Answer answer : answers) {
			System.out.println(answer);
		}		
		Map<String, Object> msg=new HashMap<>();
		msg.put("msg", "ok");
		return a;
	}
	@GetMapping("/answer")
	public Double answer() {
		return a;
	}
	
	@GetMapping("/answer2")
	public List<Question> answer2(HttpSession session) {
		User user = (User) session.getAttribute("user");
		String usercode = user.getUserCode();
		return mapper.ans(usercode);
	}
	
	@GetMapping
	public List<Question> page(
			@RequestParam(name="l",defaultValue="10",required=false) int size,
			@RequestParam(name="s",defaultValue="0",required=false) int start,HttpSession session){
		User user = (User) session.getAttribute("user");
		String usercode = user.getUserCode();
		return mapper.list(size,start,usercode);
	}

}
