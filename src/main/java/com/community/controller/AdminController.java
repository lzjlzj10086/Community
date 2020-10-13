package com.community.controller;

import com.community.pojo.Question;
import com.community.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminIndex")
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map){
        PageInfo<Question> questionsByAdmin = adminService.findQuestionsByAdmin(pageNum, 15);
        model.addAttribute("questionsByAdmin",questionsByAdmin);
        map.put("pageAdminInfo",questionsByAdmin);
        return "admin";
    }
}
