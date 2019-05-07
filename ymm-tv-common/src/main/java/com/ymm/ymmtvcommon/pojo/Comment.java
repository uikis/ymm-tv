package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Comment implements Serializable {
    private Integer id;

    private String comment;

    private String commentTime;

    private String agree;

    private String loginAccount;

    private Integer animeId;

}