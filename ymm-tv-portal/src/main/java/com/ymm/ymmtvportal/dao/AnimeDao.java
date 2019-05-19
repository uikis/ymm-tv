package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Anime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AnimeDao extends tk.mybatis.mapper.common.Mapper<Anime> {

    @Select("select * from tb_anime where state = 1")
    List<Anime> selectUpdateAnime();

    @Select("select * from tb_anime order by total_pnum desc limit 10")
    List<Anime> selectTop10();

    @Select("select * from tb_anime")
    List<Anime> selectAllAnime();

    @Select("select path from tb_chapter where chapter = #{cid} and anime_id = #{id}")
    String selectPlayPath(@Param("id") Integer id , @Param("cid") Integer cid);
}
