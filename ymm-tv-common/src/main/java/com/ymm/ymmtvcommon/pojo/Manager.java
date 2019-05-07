package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Manager implements Serializable {
    private Integer id;

    private String loginAccount;

    private String password;

    private String email;

    private String name;

}