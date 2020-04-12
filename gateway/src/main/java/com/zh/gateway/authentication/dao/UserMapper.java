package com.zh.gateway.authentication.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.gateway.authentication.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

  @Select({"select * from user where username = #{username} and status = 1"})
  User login(@Param("username") String username);
}
