package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeDao{

    @Select("select * from tb_type")
    List<Type> selectAll();
}
