package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.UserLogin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<UserLogin> {
}
