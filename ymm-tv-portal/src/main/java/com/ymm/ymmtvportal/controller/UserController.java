package com.ymm.ymmtvportal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ymm.ymmtvcommon.pojo.Comment;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvcommon.pojo.Userinfo;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("update")
    public ResponseEntity<Boolean> updateUserinfo(String userName, String date, String gender, String signature,
                                                  HttpServletRequest request){
        return ResponseEntity.ok(userService.updateUserInfo(userName, date, gender, signature, request));
    }

    @GetMapping("collectGet")
    public ResponseEntity<List<NormalShow>> collectGet(HttpServletRequest request){
        return ResponseEntity.ok(userService.collectGet(request));
    }

    @PostMapping("updateImg")
    public ResponseEntity<String> updateImg(MultipartFile file, HttpServletRequest request) throws IOException {

        return ResponseEntity.ok(userService.updateHeadImg(file, request));
    }

    @GetMapping("userinfoGet")
    public ResponseEntity<Userinfo> userinfoGet(HttpServletRequest request){
        return ResponseEntity.ok(userService.userinfoGet(request));
    }

    @PostMapping("resetPwd")
    public ResponseEntity<Boolean> resetPwd(HttpServletRequest request, String password){
        return ResponseEntity.ok(userService.resetPwd(request, password));
    }

    @PostMapping("commentGet")
    public ResponseEntity<PageResult<Comment>> pageQueryComment(@RequestParam(defaultValue = "1") int pageNum,
                                                                      @RequestParam(defaultValue = "6") int rows,
                                                                      HttpServletRequest request){
        return ResponseEntity.ok(userService.pageQueryComment(pageNum, rows, request));
    }

    @DeleteMapping("commentDelete")
    public ResponseEntity<Void> commentsDelete(@RequestParam("ids") String ids){
        List<Integer> integerList = JSON.parseArray(ids, Integer.class);
        userService.commentsDelete(integerList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("loginOut")
    public ResponseEntity<Void> loginOut(HttpServletRequest request){
        userService.loginOut(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("cancelCollect")
    public ResponseEntity<Void> cancelCollect(HttpServletRequest request, Integer id){
        userService.cancelCollect(request, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
//    public static void main(String[] args) {
////        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
////        String path1 = path.replace("target/classes/", "src/main/resources/");
////        System.out.println(path1);
//
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String path1 = path.replace("target/classes/", "src/main/resources/static/img/");
//        String realPath =   path1.replace("/", "\\");
////
//        String substring = ".jpg";
//        System.out.println("file:///"+substring);
//
//    }
}
