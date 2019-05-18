package com.ymm.ymmtvcommon.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "tb_anime")
public class Anime implements Serializable {
    @Id
    private Integer id;

    private String name;

    private String img;

    private String region;

    private String createTime;

    private String intro;

    private String type;

    @Transient
    private List<String> typeList;

    private String path;

    private Integer collectnum;

    private String chapter;

    private Integer state;

    private String updateChapter;

    @JsonIgnore
    private String thumbnail;

    private Integer commentId;

    private Integer playNum;

    private Integer totalPnum;

    private String updateTime;

    @JsonIgnore
    private String year;
}