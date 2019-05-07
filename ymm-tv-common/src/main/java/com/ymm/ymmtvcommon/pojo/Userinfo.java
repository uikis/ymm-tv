package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Table(name = "tb_userinfo")
public class Userinfo implements Serializable {

    @Id
    private String loginAccount;

    private String userName;

    private String birthday;

    private String signature;

    private String province;

    private String gender;

    private String headImg;

    private String collect;

    private String role;

}