package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.NormalShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NormalShowDao extends tk.mybatis.mapper.common.Mapper<NormalShow> {

    @Select("select * from tb_normal_show order by weight desc")
    List<NormalShow> NormalShowGet();

    @Select("select * from tb_normal_show order by play_num desc")
    List<NormalShow> hotShowGet();
}
