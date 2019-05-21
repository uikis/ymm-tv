package com.ymm.ymmtvportal.controller;

import com.ymm.ymmtvcommon.pojo.Anime;
import com.ymm.ymmtvcommon.result.PageResult;
import com.ymm.ymmtvportal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("detail")
    public ResponseEntity<PageResult<Anime>> queryItems(@RequestParam(defaultValue = "1") int pageNum,
                                                              @RequestParam(defaultValue = "4") int rows, String key) {
        return ResponseEntity.ok(searchService.queryItems(pageNum, rows, key));
    }

}
