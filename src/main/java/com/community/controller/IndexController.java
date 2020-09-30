package com.community.controller;

import com.community.pojo.Question;
import com.community.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map,String title){
        PageInfo<Question> questions = questionService.findQuestion(pageNum, 9,title);
        model.addAttribute("questions",questions);
        map.put("pageInfo",questions);
        return "index";
    }
}
