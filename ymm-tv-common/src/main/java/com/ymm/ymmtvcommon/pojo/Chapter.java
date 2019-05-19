package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Table(name = "tb_chapter")
public class Chapter implements Serializable {
    @Id
    private Integer id;

    private String path;

    private Integer chapter;

    private Integer animeId;

}