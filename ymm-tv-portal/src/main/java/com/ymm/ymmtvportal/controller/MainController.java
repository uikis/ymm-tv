package com.ymm.ymmtvportal.controller;

import com.ymm.ymmtvcommon.pojo.Carousel;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvportal.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//处理主页显示的控制器
@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    private MainService mainService;

    @PostMapping("carousel")
    public ResponseEntity<List<Carousel>> carouselGet(){
        return ResponseEntity.ok(mainService.carouselGet());
    }

    @PostMapping("portalShow")
    public ResponseEntity<List<NormalShow>> portalShowGet(){
        return ResponseEntity.ok(mainService.portalShowGet());
    }

    @PostMapping("hotShow")
    public ResponseEntity<List<NormalShow>> hotShowGet(){
        return ResponseEntity.ok(mainService.hostShowGet());
    }
}
