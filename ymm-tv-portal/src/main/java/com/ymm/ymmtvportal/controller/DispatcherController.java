package com.ymm.ymmtvportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DispatcherController {

    @GetMapping("")
    public String index(){
        return "main";
    }

    @GetMapping("regist")
    public String regist(){
        return "reg";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("sendCode")
    public String forgetPwd(){
        return "send-code";
    }

    @GetMapping("resetPwd")
    public String resetPwd(){
        return "forget-password";
    }
//    @RequestMapping("test")
//    public ResponseEntity<String> test(){
//        return ResponseEntity.ok("ok");
//    }
}
