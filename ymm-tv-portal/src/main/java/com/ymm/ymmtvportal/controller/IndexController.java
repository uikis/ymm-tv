package com.ymm.ymmtvportal.controller;

import com.ymm.ymmtvcommon.pojo.UserLogin;
import com.ymm.ymmtvportal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 处理登录注册等常规请求
 */
@RestController
public class IndexController {

    @Autowired
    private LoginService loginService;

    //注册用户
    @PostMapping("regist")
    public ResponseEntity<Boolean> registUser(String loginaccount, String password, String email) {
        //封装对象
        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(password);
        userLogin.setLoginAccount(loginaccount);
        userLogin.setEmail(email);
        return ResponseEntity.ok(loginService.registUser(userLogin));
    }

    //远程校验邮箱是否存在
    @PostMapping("remoteEmail")
    public ResponseEntity<Boolean> remoteEmail(String email) {
        return ResponseEntity.ok(loginService.remoteEmail(email));
    }

    //远程校验账号是否存在
    @PostMapping("remoteAccount")
    public ResponseEntity<Boolean> remoteAccount(String loginaccount) {
        return ResponseEntity.ok(loginService.remoteAccount(loginaccount));
    }

    //登录校验
    @PostMapping("login")
    public ResponseEntity<Void> login(String loginaccount, String password, HttpSession session,
                                            HttpServletResponse response, Integer flag) {
        loginService.login(loginaccount, password, session, response, flag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //发送邮箱验证码
    @PostMapping("sendCode")
    public ResponseEntity<Boolean> sendCode(String email){
        return ResponseEntity.ok(loginService.sendCode(email));
    }

    //重置密码
    @PostMapping("resetPwd")
    public ResponseEntity<Boolean> resetPwd(String token, String password){
        return ResponseEntity.ok(loginService.resetPwd(token, password));
    }
}
