package com.community.teststudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/quiz")
	public String quiz() {
		return "quiz";
	}
	
	@GetMapping("/questionTestStudy")
	public String question() {
		return "questionTestStudy";
	}

}
