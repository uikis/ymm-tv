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

    @GetMapping("user")
    public String user(){
        return "user";
    }

    @GetMapping("user/comment")
    public String comment(){
        return "comment";
    }

    @GetMapping("search/detail")
    public String searchDetail(){
        return "search";
    }

    @GetMapping("portal/anime")
    public String portalAnime(){
        return "portal";
    }

    @GetMapping("portal/detail")
    public String animeDetail(){
        return "detail";
    }

    @GetMapping("play/anime")
    public String animePlay(){
        return "player";
    }

    @GetMapping("/tip")
    public String tip(){
        return "tip";
    }

    @GetMapping("/portal/rank")
    public String rank(){
        return "rank";
    }

    @GetMapping("errortip")
    public String errortip(){
        return "errortip";
    }

//    @RequestMapping("test")
//    public ResponseEntity<String> test(){
//        return ResponseEntity.ok("ok");
//    }
}
