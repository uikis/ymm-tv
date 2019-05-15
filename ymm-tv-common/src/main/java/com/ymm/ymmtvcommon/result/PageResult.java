package com.ymm.ymmtvcommon.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private List<T> item;//当前页的条目
    private int pageNum;//当前页数
    private int totalPage;//总的页数
    private long total;//总的条数
}
