package com.ymm.ymmtvcommon.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    private String gender;

    private String headImg;

    private String collect;

    private String role;

    @Transient
    private String createTime;

    @Transient
    private String lastLoginTime;

    @Transient
    private String email;

}