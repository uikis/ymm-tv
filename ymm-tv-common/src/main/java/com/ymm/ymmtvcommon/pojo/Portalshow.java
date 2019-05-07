package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Portalshow implements Serializable {
    private Integer id;

    private String img;

    private String type;

    private String title;

    private String collect;

    private String update;

    private String name;

    private String detail;

    private Integer animeId;

}