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

    @Select("select * from tb_normal_show where anime_id = #{id}")
    NormalShow selectNormalShowById(Integer id);

    @Select("select * from tb_normal_show order by weight desc limit 12")
    List<NormalShow> selectAnimeTop15();

    @Select("select * from tb_normal_show a join tb_collect_user b on a.anime_id = b.anime_id and login_account = #{loginAccount}")
    List<NormalShow> selectCollectAnime(String loginAccount);
}
