package com.ymm.ymmtvportal.controller;

import com.ymm.ymmtvcommon.pojo.Anime;
import com.ymm.ymmtvcommon.pojo.Carousel;
import com.ymm.ymmtvcommon.pojo.NormalShow;
import com.ymm.ymmtvportal.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//处理主页显示的控制器
@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("carousel")
    public ResponseEntity<List<Carousel>> carouselGet(){
        return ResponseEntity.ok(mainService.carouselGet());
    }

    @GetMapping("portalShow")
    public ResponseEntity<List<NormalShow>> portalShowGet(){
        return ResponseEntity.ok(mainService.portalShowGet());
    }

    @GetMapping("hotShow")
    public ResponseEntity<List<Anime>> hotShowGet(){
        return ResponseEntity.ok(mainService.hostShowGet());
    }

    @GetMapping("updateShow")
    public ResponseEntity<List<Anime>> updateShow(){
        return ResponseEntity.ok(mainService.updateShow());
    }

    @GetMapping("rankShow")
    public ResponseEntity<List<Anime>> rankShow(){
        return ResponseEntity.ok(mainService.rankShow());
    }

    @GetMapping("animeShow")
    public ResponseEntity<List<Anime>> animeShow(){
        return ResponseEntity.ok(mainService.animeShow());
    }
}
