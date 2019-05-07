package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CarouselDao extends tk.mybatis.mapper.common.Mapper<Carousel> {

    @Select("select * from tb_carousel order by weight desc")
    List<Carousel> carouselGet();
}
