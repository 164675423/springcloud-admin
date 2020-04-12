package com.zh.am.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.am.authentication.LoginUser;
import com.zh.am.common.PageRequest;
import com.zh.am.common.Paged;
import com.zh.am.dto.user.GetUsersInput;
import com.zh.am.dto.user.GetUsersOutput;
import com.zh.am.dto.user.InsertUserDto;
import com.zh.am.dto.user.UpdatePasswordDto;
import com.zh.am.dto.user.UpdateUserDto;
import com.zh.am.entity.User;

public interface IUserService extends IService<User> {
  void insert(InsertUserDto input, LoginUser loginUser);

  void update(String userId, UpdateUserDto input, LoginUser loginUser);

  Paged<GetUsersOutput> getUsers(GetUsersInput input, PageRequest pageRequest);

  GetUsersOutput getUsersById(String id);

  void abandonUser(String id, LoginUser loginUser);

  void resetPwd(String id, LoginUser loginUser);

  void updatePassword(UpdatePasswordDto passwordDto, LoginUser loginUser);

}
