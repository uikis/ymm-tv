package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;

@Mapper
@Repository
public interface CommentDao extends tk.mybatis.mapper.common.Mapper<Comment>, IdListMapper<Comment, Integer> {
}
