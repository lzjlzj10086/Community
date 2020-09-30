package com.community.controller;

import com.community.pojo.Question;
import com.community.pojo.User;
import com.community.service.MyService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class MyController {

    @Autowired
    private MyService myService;
    @RequestMapping("/profile")
    public String toProfile(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map, String title, HttpSession session){
        User user = (User)session.getAttribute("user");
        String userCode = user.getUserCode();
        PageInfo<Question> myQuestions = myService.findMyQuestion(pageNum,9,title,userCode);
        model.addAttribute("myQuestions",myQuestions);
        map.put("mypageInfo",myQuestions);
        return "profile";
    }
    @RequestMapping("/deleteQuestion")
    public String deleteQuestion(String questionCode, Model model, RedirectAttributes attributes){
        Integer integer = myService.deleteQuestion(questionCode);
        if (integer == 0){
            attributes.addFlashAttribute("mymassage","删除失败");
        }
        attributes.addFlashAttribute("mymassage","删除成功");
        return "redirect:/profile";
    }
}
