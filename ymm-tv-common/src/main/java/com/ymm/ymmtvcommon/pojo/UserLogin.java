package com.ymm.ymmtvcommon.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Table(name = "tb_user_login")
public class UserLogin implements Serializable {

    @Id
    private Integer id;

    private String password;

    private Boolean state;

    private String loginAccount;

    private String email;

    private String createTime;

    private String lastLogintime;

    @JsonIgnore
    private String token;

    @JsonIgnore
    private String pwdToken;

}