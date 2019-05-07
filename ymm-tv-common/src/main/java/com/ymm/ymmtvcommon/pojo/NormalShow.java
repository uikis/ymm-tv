package com.ymm.ymmtvcommon.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_normal_show")
public class NormalShow implements Serializable {

    @Id
    private Integer id;

    private String thpath;

    private String chapter;

    private String title;

    @JsonIgnore
    private Integer animeId;

    private String update;

    private String collecte;

    private String detail;

    private Integer playNum;

    private String path;

    private String wpath;

    private String weight;

}