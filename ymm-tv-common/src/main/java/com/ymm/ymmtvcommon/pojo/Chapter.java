package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Chapter implements Serializable {
    private Integer id;

    private String path;

    private Integer chapter;

    private Integer animeId;

}