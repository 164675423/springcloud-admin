package com.zh.am.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.am.domain.dto.common.IdName;
import com.zh.am.domain.dto.role.RoleDto;
import com.zh.am.domain.dto.role.SaveRoleDto;
import com.zh.am.domain.entity.Role;
import com.zh.common.context.LoginUser;

import java.util.List;


public interface RoleService extends IService<Role> {
  List<IdName> getRoleIdName();

  List<RoleDto> getRoles(Integer status);

  void abandon(String id, LoginUser loginUser);

  void editRoleInfo(String id, String name, LoginUser loginUser);

  void save(SaveRoleDto roleDto, LoginUser loginUser);

  void edit(String id, SaveRoleDto roleDto, LoginUser loginUser);

  RoleDto getRole(String id);
}
