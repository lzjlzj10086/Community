package com.community.teststudy.controller;

import com.community.teststudy.pojo.Question;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {
	
	@GetMapping("/about")
	public Map<String, Object> about() {
		Map<String, Object> data=new HashMap<>();
		data.put("version", 1.1);
		data.put("author", "ltan");
		data.put("title", "在线测试平台");
		return data;
	}
	@PostMapping("/")
	public Map<String, Object> answer(@RequestParam(name="id") int id,
			@RequestParam(name="answer") String answer) {
		System.out.printf("%d:%s\n", id,answer);
		Map<String, Object> data=new HashMap<>();
		data.put("msg", "ok");		
				return data;
	}
	
	@GetMapping
	public List<Question> list(){
		List<Question> list=new ArrayList<>();
		
//		Question q1=new Question(1,"最长的河");
//		Question q2=new Question(2,"市值最高的公司");
//		Question q3=new Question(3,"第一个程序员");
//		
//		q1.items.add("黄河");
//		q1.items.add("尼罗河");
//		q1.items.add("亚马逊");
//		q1.items.add("莱茵河");
//		
//		q2.items.add("app");
//		q2.items.add("b");
//		q2.items.add("c");
//		q2.items.add("d");
//		
//		q3.items.add("e");
//		q3.items.add("f");
//		q3.items.add("g");
//		q3.items.add("h");
//		
//		list.add(q1);
//		list.add(q2);
//		list.add(q3);
		return list;
		
		
	}

}
