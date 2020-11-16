package com.zh.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zh.am.domain.dao.RoleMapper;
import com.zh.am.domain.dao.UserMapper;
import com.zh.am.domain.dao.UserRoleMapper;
import com.zh.am.domain.dto.common.IdName;
import com.zh.am.domain.dto.user.GetUsersInput;
import com.zh.am.domain.dto.user.GetUsersOutput;
import com.zh.am.domain.dto.user.InsertUserDto;
import com.zh.am.domain.dto.user.UpdatePasswordDto;
import com.zh.am.domain.dto.user.UpdateUserDto;
import com.zh.am.domain.entity.Role;
import com.zh.am.domain.entity.User;
import com.zh.am.domain.entity.UserRole;
import com.zh.am.domain.mapStruct.UserMapStruct;
import com.zh.am.service.UserService;
import com.zh.common.constants.Enums;
import com.zh.common.context.LoginUser;
import com.zh.common.exception.BusinessException;
import com.zh.common.page.PageRequest;
import com.zh.common.page.Paged;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final RoleMapper roleMapper;
  private final UserRoleMapper userRoleMapper;
  private final UserMapStruct userMapStruct;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper, RoleMapper roleMapper,
                         UserRoleMapper userRoleMapper, UserMapStruct userMapStruct) {
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.roleMapper = roleMapper;
    this.userRoleMapper = userRoleMapper;
    this.userMapStruct = userMapStruct;
  }

  @Override
  @Transactional
  public void insert(InsertUserDto input, LoginUser loginUser) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(User::getUsername, input.getUsername().toLowerCase())
        .eq(User::getStatus, Enums.BaseDataStatus.有效.getIndex());
    if (userMapper.selectCount(wrapper) > 0) {
      throw new BusinessException("用户名已存在");
    }
    if (input.getRoles() == null || input.getRoles().size() == 0) {
      throw new BusinessException("角色不可为空");
    }
    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setName(input.getName());
    user.setUsername(input.getUsername().toLowerCase());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    user.setCreatorId(loginUser.getId());
    user.setCreateTime(new Date());
    user.setCreatorName(loginUser.getUserName());
    user.setStatus(Enums.BaseDataStatus.有效.getIndex());
    user.setReadonly(false);
    userMapper.insert(user);

    insertUserRoles(user, input.getRoles());
  }

  private void insertUserRoles(User user, List<String> roleIds) {
    List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().lambda().
        eq(Role::getStatus, Enums.BaseDataStatus.有效.getIndex())
        .in(Role::getId, roleIds));
    roleIds.forEach(roleId -> {
      Role role = roles.stream().filter(pre -> pre.getId().equalsIgnoreCase(roleId)).findAny().orElse(null);
      if (role == null) {
        throw new BusinessException("角色不存在或已作废");
      }
      UserRole userRole = new UserRole();
      userRole.setRoleId(roleId);
      userRole.setUserId(user.getId());
      userRoleMapper.insert(userRole);
    });
  }

  @Override
  @Transactional
  public void update(String userId, UpdateUserDto input, LoginUser loginUser) {
    User user = userMapper.selectById(userId);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    if (input.getRoles().size() == 0) {
      throw new BusinessException("角色不能为空");
    }
    user.setName(input.getName());
    user.setModifierId(loginUser.getId());
    user.setModifierName(loginUser.getName());
    user.setModifyTime(new Date());
    userMapper.updateById(user);
    //删除原有角色
    userRoleMapper.delete(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, user.getId()));
    insertUserRoles(user, input.getRoles());
  }

  @Override
  public Paged<GetUsersOutput> getUsers(GetUsersInput input, PageRequest pageRequest) {
    Page<Object> page = PageHelper.startPage(pageRequest.getPageIndex(), pageRequest.getPageSize());
    List<GetUsersOutput> users = userMapper.getAllUsers(input, pageRequest.getSortField(), pageRequest.getIsDesc());
    return Paged.From(page, users);
  }

  @Override
  public GetUsersOutput getUsersById(String id) {
    User user = userMapper.selectById(id);
    GetUsersOutput output = userMapStruct.entityToGetUsers(user);
    List<Role> roles = roleMapper.getRolesByUserId(user.getId());
    output.setRoles(roles.stream().map(role -> {
      IdName idName = new IdName();
      idName.setId(role.getId());
      idName.setName(role.getName());
      return idName;
    }).collect(Collectors.toList()));
    return output;
  }

  @Override
  @Transactional
  public void abandonUser(String id, LoginUser loginUser) {
    User user = userMapper.selectById(id);
    user.setStatus(Enums.BaseDataStatus.作废.getIndex());
    user.setModifierId(loginUser.getId());
    user.setModifierName(loginUser.getName());
    user.setModifyTime(new Date());
    userMapper.updateById(user);
  }

  @Override
  @Transactional
  public void resetPwd(String id, LoginUser loginUser) {
    User user = userMapper.selectById(id);
    user.setPassword(passwordEncoder.encode("123456"));
    user.setModifierId(loginUser.getId());
    user.setModifierName(loginUser.getName());
    user.setModifyTime(new Date());
    userMapper.updateById(user);
  }

  @Override
  @Transactional
  public void updatePassword(UpdatePasswordDto passwordDto, LoginUser loginUser) {
    User user = userMapper.selectById(loginUser.getId());
    if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
      throw new BusinessException("原密码错误");
    }
    user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
    user.setModifierId(loginUser.getId());
    user.setModifierName(loginUser.getName());
    user.setModifyTime(new Date());
    userMapper.updateById(user);
  }

}
