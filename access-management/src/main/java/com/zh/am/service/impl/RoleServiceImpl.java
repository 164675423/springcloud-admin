package com.zh.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.authentication.LoginUser;
import com.zh.am.common.constant.Enums;
import com.zh.am.domain.dao.PageMapper;
import com.zh.am.domain.dao.PermissionMapper;
import com.zh.am.domain.dao.RoleMapper;
import com.zh.am.domain.dto.common.IdName;
import com.zh.am.domain.dto.role.RoleDto;
import com.zh.am.domain.dto.role.SaveRoleDto;
import com.zh.am.domain.entity.Page;
import com.zh.am.domain.entity.Permission;
import com.zh.am.domain.entity.Role;
import com.zh.am.domain.mapStruct.RoleMapStruct;
import com.zh.am.common.exception.BusinessException;
import com.zh.am.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
  private final RoleMapper roleMapper;
  private final RoleMapStruct roleMapStruct;
  private final PermissionMapper permissionMapper;
  private final PageMapper pageMapper;

  public RoleServiceImpl(RoleMapper roleMapper, RoleMapStruct roleMapStruct, PermissionMapper permissionMapper, PageMapper pageMapper) {
    this.roleMapper = roleMapper;
    this.roleMapStruct = roleMapStruct;
    this.permissionMapper = permissionMapper;
    this.pageMapper = pageMapper;
  }

  @Override
  public List<IdName> getRoleIdName() {
    List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>()
        .lambda()
        .eq(Role::getStatus, Enums.BaseDataStatus.有效.getIndex()));
    return roles.stream().map(r -> {
      IdName idName = new IdName();
      idName.setId(r.getId());
      idName.setName(r.getName());
      return idName;
    }).collect(Collectors.toList());
  }

  @Override
  public List<RoleDto> getRoles(Integer status) {
    List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>()
        .lambda()
        .eq(Role::getStatus, status));
    return roleMapStruct.entityToDTOList(roles);
  }

  private Role validateRole(String roleId) {
    Role role = roleMapper.selectById(roleId);
    if (role == null) {
      throw new BusinessException("该角色不存在");
    }
    if (role.getReadonly()) {
      throw new BusinessException("该角色不允许编辑");
    }
    return role;
  }

  @Transactional
  @Override
  public void abandon(String id, LoginUser loginUser) {
    Role role = validateRole(id);
    role.setStatus(Enums.BaseDataStatus.作废.getIndex());
    role.setModifierId(loginUser.getId());
    role.setModifierName(loginUser.getName());
    role.setModifyTime(new Date());
    roleMapper.updateById(role);
  }

  @Override
  public void editRoleInfo(String id, String name, LoginUser loginUser) {
    Role role = validateRole(id);
    if (name.equals(role.getName())) {
      checkDuplicateRoleName(id, name);
    }
    role.setName(name);
    role.setModifierId(loginUser.getId());
    role.setModifierName(loginUser.getName());
    role.setModifyTime(new Date());
    roleMapper.updateById(role);
  }

  private void checkDuplicateRoleName(String id, String roleName) {
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(Role::getName, roleName.toLowerCase()).eq(Role::getId, id);
    List<Role> roles = roleMapper.selectList(queryWrapper);
    if (roles != null && roles.size() > 0) {
      throw new BusinessException("角色名称已经存在");
    }
  }

  @Transactional
  @Override
  public void save(SaveRoleDto roleDto, LoginUser user) {
    checkDuplicateRoleName("", roleDto.getName());
    Role role = roleMapStruct.dtoToEntity(roleDto);
    role.setStatus(Enums.BaseDataStatus.有效.getIndex());
    role.setId(UUID.randomUUID().toString());
    role.setCreatorId(user.getId());
    role.setCreatorName(user.getName());
    role.setCreateTime(new Date());
    roleMapper.insert(role);
    addPermission(roleDto, role.getId());
  }

  @Transactional
  @Override
  public void edit(String id, SaveRoleDto roleDto, LoginUser user) {
    Role roleOrig = roleMapper.selectById(id);
    if (roleOrig != null) {
      if (roleOrig.getReadonly()) {
        throw new BusinessException("该角色不允许编辑");
      }
      if (!roleDto.getName().equals(roleOrig.getName())) {
        checkDuplicateRoleName(id, roleDto.getName());
      }
      Role role = roleMapStruct.dtoToEntity(roleDto);
      role.setModifierId(user.getId());
      role.setModifierName(user.getName());
      role.setModifyTime(new Date());

      roleMapper.updateById(role);

      //角色对应的page operation ,删除再插入新数据
      permissionMapper.delete(new QueryWrapper<Permission>().lambda().eq(Permission::getRoleId, id));
      addPermission(roleDto, id);
    }

  }

  @Override
  public RoleDto getRole(String id) {
    return roleMapStruct.entityToDTO(roleMapper.selectById(id));
  }

  private void addPermission(SaveRoleDto roleDTO, String roleId) {
    List<String> operations = roleDTO.getOperations();
    if (operations != null && operations.size() > 0) {
      for (String operation : operations) {
        Permission permission = new Permission();
        permission.setRoleId(roleId);
        permission.setRelatedId(operation);
        permission.setRelatedType(Enums.RelatedType.按钮.getIndex());
        permissionMapper.insert(permission);
      }
    }
    List<Page> allPages = pageMapper.selectList(null);
    List<String> pages = roleDTO.getPages();
    if (pages != null && pages.size() > 0) {
      ArrayList<String> list = new ArrayList<>();
      for (String page : pages) {
        Page dbPage = allPages.stream().filter(r -> r.getId().equals(page)).findAny().orElse(null);
        if (dbPage == null) {
          throw new BusinessException("page不存在");
        }
        list.add(dbPage.getId());
        if (StringUtils.isNotBlank(dbPage.getParentId())) {
          addParentPage(allPages, dbPage.getParentId(), list);
        }
      }
      list.stream().distinct().forEach(pageId -> {
        Permission permission = new Permission();
        permission.setRoleId(roleId);
        permission.setRelatedId(pageId);
        permission.setRelatedType(Enums.RelatedType.页面.getIndex());
        permissionMapper.insert(permission);
      });
    }
  }

  private void addParentPage(List<Page> allPages, String parentId, List<String> parentIds) {
    if (parentIds == null) {
      parentIds = new ArrayList<>();
    }
    Page parent = allPages.stream().filter(r -> r.getId().equals(parentId)).findAny().orElse(null);
    if (parent != null) {
      parentIds.add(parent.getId());
    }
    if (StringUtils.isNotBlank(parent.getParentId())) {
      addParentPage(allPages, parent.getParentId(), parentIds);
    }
  }
}
