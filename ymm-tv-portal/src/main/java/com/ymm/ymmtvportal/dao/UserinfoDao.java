package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserinfoDao extends tk.mybatis.mapper.common.Mapper<Userinfo> {
}
