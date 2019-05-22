package com.ymm.ymmtvportal.config;

import com.ymm.ymmtvcommon.pojo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class Email {

    //注入邮件发送
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(UserLogin user) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("m13882055119@163.com");
            helper.setTo(user.getEmail());
            helper.setSubject("标题：您的重置code已经到了哟~");
            String tt = user.getPwdToken();
            StringBuffer sb = new StringBuffer();
            sb.append("<strong>您的重置code是："+tt+" 请妥善保管！</strong>");
            helper.setText(sb.toString(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }
}