package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Permission implements Serializable {
    private Integer id;

    private String permission;


}