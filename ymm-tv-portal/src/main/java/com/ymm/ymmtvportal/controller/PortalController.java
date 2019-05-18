package com.ymm.ymmtvportal.controller;

import com.ymm.ymmtvcommon.pojo.*;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.service.AnimeService;
import com.ymm.ymmtvportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("portal")
public class PortalController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private AnimeService animeService;

    @PostMapping("animeGet")
    public ResponseEntity<PageResult<NormalShow>> animeGet(@RequestBody(required = false) SearchRequest request){
        return ResponseEntity.ok(searchService.animeGet(request));
    }

    @GetMapping("typeGet")
    public ResponseEntity<List<Type>> typeGet(){
        return ResponseEntity.ok(searchService.typeGet());
    }

    @GetMapping("animeDetail")
    public ResponseEntity<Anime> animeDetail(Integer id){
        return ResponseEntity.ok(animeService.animeDetail(id));
    }

    @GetMapping("commentGet")
    public ResponseEntity<PageResult<Comment>> commentGet(@RequestParam(defaultValue = "1") int pageNum,
                                                    @RequestParam(defaultValue = "5") int rows, Integer id){
        return ResponseEntity.ok(animeService.commentGet(pageNum, rows, id));
    }

    @PostMapping("commentAdd")
    public ResponseEntity<Boolean> commentAdd(HttpServletRequest request, Integer id, String comment){

        return ResponseEntity.ok(animeService.commentAdd(request, id, comment));
    }

    @GetMapping("commentStateCheck")
    public ResponseEntity<List<Integer>> commentCheck(HttpServletRequest request){
        return ResponseEntity.ok(animeService.commentCheck(request));
    }

    @PostMapping("commentAgree")
    public ResponseEntity<Boolean> commentAgree(HttpServletRequest request, @RequestParam("id") Integer id){
        return ResponseEntity.ok(animeService.commentAgree(request, id));
    }
}
