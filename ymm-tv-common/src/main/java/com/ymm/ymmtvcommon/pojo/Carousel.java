package com.ymm.ymmtvcommon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_carousel")
public class Carousel implements Serializable {
    @Id
    private Integer id;

    private String path;

    private String title;

    private Integer animeId;

    private String weight;
}