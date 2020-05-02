package com.zh.am.domain.mapStruct;

import com.zh.am.domain.dto.user.GetUsersOutput;
import com.zh.am.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
  GetUsersOutput entityToGetUsers(User user);
}
