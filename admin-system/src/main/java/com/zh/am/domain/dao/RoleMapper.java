package com.zh.am.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.am.domain.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

  @Select( {"select r.* from role r\n" +
      "inner join user_role ur on ur.role_id = r.id\n" +
      "where ur.user_id = #{userId}"})
  List<Role> getRolesByUserId(@Param("userId") String userId);
}
