package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import java.util.List;

@Mapper
@Repository
public interface CommentDao extends tk.mybatis.mapper.common.Mapper<Comment>, IdListMapper<Comment, Integer> {

    @Select("select * from tb_comment where anime_id = #{id} order by comment_time desc")
    List<Comment> queryAll(Integer id);

    @Select("select id from tb_comment a join tb_comment_agree b on a.id = b.comment_id and b.login_account = #{account}")
    List<Integer> selectCommented(String account);

    @Insert("insert into tb_comment_agree (comment_id, login_account) values(#{id}, #{loginAccount})")
    int addAgree(@Param("id") Integer id, @Param("loginAccount")String loginAccount);
}
