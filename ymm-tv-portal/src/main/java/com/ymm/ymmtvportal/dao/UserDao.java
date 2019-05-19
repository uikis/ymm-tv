package com.ymm.ymmtvportal.dao;

import com.ymm.ymmtvcommon.pojo.UserLogin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<UserLogin> {

    @Delete("delete from tb_collect_user where anime_id = #{id} and login_account = #{loginAccount}")
    int cancelCollect(@Param("loginAccount") String loginAccount, @Param("id") Integer id);

}
