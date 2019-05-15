package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Table(name = "tb_comment")
public class Comment implements Serializable {

    @Id
    private Integer id;

    private String comment;

    private String commentTime;

    private String agree;

    private String loginAccount;

    private Integer animeId;

    @Transient
    private String animeName;
}