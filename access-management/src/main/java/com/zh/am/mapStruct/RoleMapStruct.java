package com.zh.am.mapStruct;

import com.zh.am.dto.role.RoleDto;
import com.zh.am.dto.role.RoleVo;
import com.zh.am.dto.role.SaveRoleDto;
import com.zh.am.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapStruct {
  List<RoleDto> entityToDTOList(List<Role> roles);

  List<RoleVo> dtoToVOList(List<RoleDto> roles);

  Role dtoToEntity(SaveRoleDto saveRoleDTO);

  @Mapping(source = "readonly", target = "readOnly")
  RoleDto entityToDTO(Role role);

  RoleVo dtoToVO(RoleDto roleDTO);

  List<Role> dtoToEntityList(List<RoleDto> roleDTOList);
}
