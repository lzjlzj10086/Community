package com.community.codeeditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JavaController {

    @RequestMapping("javaExecute")
    public String javaExecute(){
        return "javaIndex";
    }
}
