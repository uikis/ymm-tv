package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Kind implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

}