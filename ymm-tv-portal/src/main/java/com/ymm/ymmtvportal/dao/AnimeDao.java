package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Anime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;

import java.util.List;

@Repository
@Mapper
public interface AnimeDao extends tk.mybatis.mapper.common.Mapper<Anime>, IdListMapper<Anime, Integer> {

    @Select("select * from tb_anime where state = 1")
    List<Anime> selectUpdateAnime();

    @Select("select name,id from tb_anime order by total_pnum desc limit 10")
    List<Anime> selectTop10();

    @Select("select * from tb_anime order by play_num desc limit 5")
    List<Anime> selectTodayTop5();

    @Select("select * from tb_anime")
    List<Anime> selectAllAnime();

    @Select("select path from tb_chapter where chapter = #{cid} and anime_id = #{id}")
    String selectPlayPath(@Param("id") Integer id , @Param("cid") Integer cid);

    @Select("select * from tb_anime order by create_time desc limit 12")
    List<Anime> selectCommend();
}
