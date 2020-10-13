package com.community.controller;

import com.community.pojo.Comment;
import com.community.pojo.Question;
import com.community.service.AdminService;
import com.community.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/adminIndex")
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map){
        PageInfo<Question> questionsByAdmin = adminService.findQuestionsByAdmin(pageNum, 10);
        model.addAttribute("questionsByAdmin",questionsByAdmin);
        map.put("pageAdminInfo",questionsByAdmin);
        return "admin";
    }

    @RequestMapping("/adminQuestion")
    public String findQuestionById(String questionCode, Model model, RedirectAttributes attributes){
        Question question = questionService.findQuestionById(questionCode);
        if(question == null){
            attributes.addFlashAttribute("massage","该话题发生错误");
            return "redirect:/adminIndex";
        }
        model.addAttribute("adminQuestion",question);
        return "adminQuestion";
    }
    @RequestMapping("/updateStatus")
    public String updateQuestionStatusById(Question question, Model model){
        Integer statusById = adminService.updateQuestionStatusById(question);
        if (statusById == 0){
            model.addAttribute("statusMessage","修改发生错误");
        }else {
            model.addAttribute("statusMessage","修改成功");
        }
        return "redirect:/adminIndex";
    }
    @RequestMapping("/adminPassQuestion")
    public String adminPassQuestion(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map,String title){
        PageInfo<Question> questions = questionService.findQuestion(pageNum, 10,title);
        model.addAttribute("PassQuestions",questions);
        map.put("PassPageInfo",questions);
        return "adminPassQuestion";
    }
    @RequestMapping("/adminNotPassQuestion")
    public String adminNotPassQuestion(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map,String title){
        PageInfo<Question> questions = adminService.findQuestionsByAdminOfNotPass(pageNum, 10);
        model.addAttribute("PassQuestions",questions);
        map.put("NotPassPageInfo",questions);
        return "adminNotPassQuestion";
    }
}
