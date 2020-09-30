package com.community.controller;

import com.community.pojo.Question;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @RequestMapping("/question")
    public String findQuestionById(String questionCode, Model model, RedirectAttributes attributes){
        Question question = questionService.findQuestionById(questionCode);
        if(question == null){
            attributes.addFlashAttribute("massage","该话题发生错误");
            return "redirect:/index";
        }
        model.addAttribute("question",question);
        return "question";
    }
    @RequestMapping("/updateQuestion")
    public String updateQuestion(Question question,RedirectAttributes attributes){
        Integer integer = questionService.updateQuestion(question);
        if (integer == 0){
            attributes.addFlashAttribute("updatemassage","修改失败");
        }
        attributes.addFlashAttribute("updatemassage","修改成功");
        return "redirect:/profile";
    }
    @RequestMapping("/toupdateQuestion")
    public String toupdateQuestion(String questionCode, Model model, RedirectAttributes attributes){
        Question question = questionService.findQuestionById(questionCode);
        model.addAttribute("updatequestion",question);
        return "updateQuestion";
    }
}
