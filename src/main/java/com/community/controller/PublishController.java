package com.community.controller;

import com.community.mapper.QuestionMapper;
import com.community.pojo.Question;
import com.community.pojo.User;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @RequestMapping("topublish")
    public String toPublish(){
        return "publish";
    }
    @RequestMapping("publish")
    public String publish(Question question,RedirectAttributes attributes, HttpSession session){
        User user = (User)session.getAttribute("user");
        System.out.println(user);
        question.setUserCode(user.getUserCode());
        int saveQuestion = questionService.saveQuestion(question);
        if(saveQuestion == 0){
            attributes.addFlashAttribute("massage","发布话题失败");
            return "redirect:/topublish";
        }
        return "redirect:/profile";
    }
}
