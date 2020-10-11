package com.community.controller;

import com.community.pojo.Notify;
import com.community.pojo.Question;
import com.community.pojo.User;
import com.community.service.NotifyService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class NotifyController {

    @Autowired
    private NotifyService notifyService;


    @RequestMapping("findNotifys")
    public String findNotifys(@RequestParam(defaultValue = "1") Integer pageNum, Model model, Map<String,Object> map, HttpSession session){
        User user =(User)session.getAttribute("user");
        PageInfo<Notify> notifys = notifyService.findNotifys(user.getUserCode(), pageNum, 15);
        model.addAttribute("notifys",notifys);
        map.put("notifyInfo",notifys);
        return "notify";
    }
}
