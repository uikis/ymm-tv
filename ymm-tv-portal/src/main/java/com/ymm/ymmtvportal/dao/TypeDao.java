package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TypeDao extends tk.mybatis.mapper.common.Mapper<Type> {
}
