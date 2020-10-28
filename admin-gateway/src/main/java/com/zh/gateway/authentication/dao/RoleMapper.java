package com.zh.gateway.authentication.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.gateway.authentication.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
  @Select({"SELECT r.name FROM `user` u INNER JOIN user_role ur ON u.id=ur.user_id INNER JOIN role r ON ur.role_id=r.id WHERE u.id = #{userId}"})
  List<String> getRolesByUserId(@Param("userId") String userId);
}
