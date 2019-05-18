package com.ymm.ymmtvcommon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest implements Serializable {

    private Integer pageNum;
    private Integer rows;
    private String order;
    private String type;
    private String region;
    private String year;
}
