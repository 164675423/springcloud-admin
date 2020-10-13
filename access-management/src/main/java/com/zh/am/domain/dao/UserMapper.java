package com.zh.am.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.am.domain.dto.user.GetUsersInput;
import com.zh.am.domain.dto.user.GetUsersOutput;
import com.zh.am.domain.entity.User;
import com.zh.common.context.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
  LoginUser getLoginUser(@Param("userId") String userId);

  List<GetUsersOutput> getAllUsers(@Param("input") GetUsersInput input, @Param("sortField") String sortField, @Param("isDesc") boolean isDesc);
}
