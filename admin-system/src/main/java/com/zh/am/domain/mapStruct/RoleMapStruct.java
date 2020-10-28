package com.zh.am.domain.mapStruct;

import com.zh.am.domain.dto.role.RoleDto;
import com.zh.am.domain.dto.role.RoleVo;
import com.zh.am.domain.dto.role.SaveRoleDto;
import com.zh.am.domain.entity.Role;
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
