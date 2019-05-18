package com.ymm.ymmtvcommon.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_normal_show")
public class NormalShow implements Serializable {

    @Id
    private Integer animeId;

    private String thpath;

    private String chapter;

    private String title;

    //解决冲突，
    @Column(name = "updated")
    private String update;

    private String collect;

    private String detail;

    private Integer playNum;

    private String path;

    private String wpath;

    @JsonIgnore
    private String weight;

    @Transient
    private String updateTime;

}