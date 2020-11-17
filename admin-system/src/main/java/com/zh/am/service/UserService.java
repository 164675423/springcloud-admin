package com.zh.am.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.am.domain.dto.user.GetUsersInput;
import com.zh.am.domain.dto.user.GetUsersOutput;
import com.zh.am.domain.dto.user.InsertUserDto;
import com.zh.am.domain.dto.user.UpdatePasswordDto;
import com.zh.am.domain.dto.user.UpdateUserDto;
import com.zh.am.domain.entity.User;
import com.zh.common.base.context.LoginUser;
import com.zh.common.base.page.PageRequest;
import com.zh.common.base.page.Paged;

public interface UserService extends IService<User> {
  void insert(InsertUserDto input, LoginUser loginUser);

  void update(String userId, UpdateUserDto input, LoginUser loginUser);

  Paged<GetUsersOutput> getUsers(GetUsersInput input, PageRequest pageRequest);

  GetUsersOutput getUsersById(String id);

  void abandonUser(String id, LoginUser loginUser);

  void resetPwd(String id, LoginUser loginUser);

  void updatePassword(UpdatePasswordDto passwordDto, LoginUser loginUser);

}
