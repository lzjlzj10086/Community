package com.community.controller;

import com.community.pojo.User;
import com.community.service.LoignService;
import com.community.util.CodeUtil;
import com.community.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
            //request.getServletContext().setAttribute("user",loginUser);
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
       /* if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        String path = "D:/images";
        File dest = new File(path + "/" + CodeUtil.ImageCodeRandom()+fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            attributes.addAttribute("massage","头像上传有问题");
            return "redirect:/toRegister";
        }*/
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
}
