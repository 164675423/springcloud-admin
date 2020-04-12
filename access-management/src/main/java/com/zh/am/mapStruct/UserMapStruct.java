package com.zh.am.mapStruct;

import com.zh.am.dto.user.GetUsersOutput;
import com.zh.am.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
  GetUsersOutput entityToGetUsers(User user);
}
