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

    private String thimg;

    @Transient
    private List<String> typeList;

    private Integer collectnum;

    private String chapter;

    private Integer state;

    private String updateChapter;

    private Integer playNum;

    private Integer totalPnum;

    private String updateTime;

    @Transient
    private String playPath;

    @JsonIgnore
    private String year;
}