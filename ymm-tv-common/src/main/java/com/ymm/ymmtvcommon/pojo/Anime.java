package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Anime implements Serializable {
    private Integer id;

    private String name;

    private String img;

    private String region;

    private String creatTime;

    private String intro;

    private String type;

    private String path;

    private Integer collectnum;

    private Integer chapter;

    private Integer state;

    private Integer updateChapter;

    private String thumbnail;

    private Integer commentId;

    private Integer playNum;

    private Integer totalPnum;

    private String updateTime;

}