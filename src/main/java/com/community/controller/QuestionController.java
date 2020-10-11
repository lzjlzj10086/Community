package com.community.controller;

import com.community.pojo.Comment;
import com.community.pojo.Question;
import com.community.pojo.User;
import com.community.pojo.msg;
import com.community.service.QuestionService;
import com.community.util.CodeUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @RequestMapping("/question")
    public String findQuestionById(String questionCode, Model model, RedirectAttributes attributes){
        Question question = questionService.findQuestionById(questionCode);
        List<Comment> comments = questionService.findComments(questionCode);
        if(question == null){
            attributes.addFlashAttribute("massage","该话题发生错误");
            return "redirect:/index";
        }
        List<Question> aboutQuestion = questionService.aboutQuestion(question);
        model.addAttribute("question",question);
        model.addAttribute("comments", comments);
        model.addAttribute("aboutQuestions",aboutQuestion);
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
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @Transactional
    public Object saveComment(@RequestBody Comment comment, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            msg m = new msg(201,"请先登录再评论");
            return m;
        }
        comment.setCommentCode(CodeUtil.CommentCodeRandom());
        comment.setUserCode(user.getUserCode());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setUpdateTime(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        System.out.println(comment.getReceiverCode());
        Integer saveComment = questionService.saveComment(comment,comment.getReceiverCode());
        if (saveComment != 0){
            msg m = new msg(200,"评论成功");
            return m;
        }else {
            msg m = new msg(202,"评论发生错误");
            return m;
        }
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public List<Comment> comments(@PathVariable(name = "id") String id) {
        List<Comment> comments = questionService.subCommet(id);
        return comments;
    }
}
