package com.community.controller;

import com.community.pojo.User;
import com.community.service.LoignService;
import com.community.service.NotifyService;
import com.community.util.CodeUtil;
import com.community.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class LoginController {

    @Autowired
    private LoignService loignService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private upload upload;
    @RequestMapping("tologin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("login")
    public String Login(User user, HttpSession session, RedirectAttributes attributes,HttpServletRequest request){
        User loginUser = loignService.loginUser(user);
        if (loginUser != null){
            loginUser.setPassWord(null);
            session.setAttribute("user",loginUser);
            Integer countNotifys = notifyService.countNotifys(loginUser.getUserCode());
            session.setAttribute("countNotifys",countNotifys);
            if ("root".equals(loginUser.getUserName())){
                return "redirect:/adminIndex";
            }
            return "redirect:/index";
        }else {
            attributes.addFlashAttribute("massage","用户名或密码错误");
            return "redirect:/tologin";
        }

    }
    @RequestMapping("toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     * 注册用户
     * @param user
     * @param file
     * @param attributes
     * @return
     */
    @RequestMapping("register")
    @Transactional
    public String Register(User user, MultipartFile file, RedirectAttributes attributes, HttpServletRequest request,HttpSession session){
        String upload = (String)this.upload.Upload(file, session);
        user.setUserCode(CodeUtil.CodeRandom());
        System.out.println(upload);
        user.setImageUrl(upload);
        user.setPassWord(MD5Util.code(user.getPassWord()));
        int register = loignService.register(user);
        if (register == 0){
            attributes.addFlashAttribute("massage","注册失败，用户名已经存在");
            return "redirect:/toRegister";
        }
        attributes.addFlashAttribute("massage","注册成功,请登录");
        return "redirect:/tologin";
    }
    @RequestMapping("logintOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/tologin";
    }
    @RequestMapping("updateUser")
    @Transactional
    public String updateUser(User user, MultipartFile file, RedirectAttributes attributes, HttpServletRequest request,HttpSession session){
        System.out.println("filename:"+file);
        if (!file.equals(null)){
            String upload = (String)this.upload.Upload(file, session);
            user.setImageUrl(upload);
        }
        User updateUser = loignService.findUpdateUser(user);
        if (!updateUser.getPassWord().equals(user.getPassWord())){
            user.setPassWord(MD5Util.code(user.getPassWord()));
        }
        int register = loignService.updateUser(user);
        if (register == 0){
            attributes.addFlashAttribute("massage","修改失败，用户名已经存在");
            return "redirect:/findUpdateUser";
        }
        attributes.addFlashAttribute("massage","修改成功,请重新登录");
        return "redirect:/tologin";
    }
    @RequestMapping("findUpdateUser")
    public String findUpdateUser(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        User findUpdateUser = loignService.findUpdateUser(user);
        model.addAttribute("updateUser",findUpdateUser);
        return "updateUser";
    }
}
