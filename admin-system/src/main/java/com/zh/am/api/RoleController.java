package com.zh.am.api;

import com.zh.am.domain.dto.page.GetPageOutput;
import com.zh.am.domain.dto.role.RoleDto;
import com.zh.am.domain.dto.role.RoleVo;
import com.zh.am.domain.dto.role.SaveRoleDto;
import com.zh.am.domain.mapStruct.PageMapStruct;
import com.zh.am.domain.mapStruct.RoleMapStruct;
import com.zh.am.service.PageService;
import com.zh.am.service.RoleService;
import com.zh.common.constants.Enums;
import com.zh.common.context.LoginUser;
import com.zh.common.contract.ResponseBodyWrapper;
import com.zh.common.log.aspect.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "角色信息")
@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
  private final RoleService roleService;
  private final RoleMapStruct roleMapStruct;
  private final PageMapStruct pageMapStruct;
  private final PageService pageService;

  public RoleController(RoleService roleService, RoleMapStruct roleMapStruct, PageMapStruct pageMapStruct, PageService pageService) {
    this.roleService = roleService;
    this.roleMapStruct = roleMapStruct;
    this.pageMapStruct = pageMapStruct;
    this.pageService = pageService;
  }

  @ApiOperation("获取角色列表")
  @GetMapping()
  @ApiLog("获取角色列表")
  public ResponseBodyWrapper getRoles(@RequestParam(value = "status", required = false) Integer status) {
    if (status == null) {
      status = Enums.BaseDataStatus.有效.getIndex();
    }
    List<RoleDto> roles = roleService.getRoles(status);
    return ResponseBodyWrapper.from(roleMapStruct.dtoToVOList(roles));
  }

  @ApiOperation("新增角色")
  @PostMapping
  @ApiLog("新增角色")
  public ResponseBodyWrapper add(@RequestBody @Validated SaveRoleDto saveRoleDTO,
                                 @RequestAttribute LoginUser user) {
    roleService.save(saveRoleDTO, user);
    return ResponseBodyWrapper.from("成功");
  }

  @ApiOperation("修改角色")
  @PutMapping(value = "{id}")
  @ApiLog("修改角色")
  public ResponseBodyWrapper edit(@PathVariable String id, @RequestBody @Validated SaveRoleDto editRoleDto,
                                  @RequestAttribute LoginUser user) {
    roleService.edit(id, editRoleDto, user);
    return ResponseBodyWrapper.from("修改成功");
  }

  @ApiOperation("作废角色")
  @PutMapping(value = "{id}/abandon")
  @ApiLog("作废角色")
  public ResponseBodyWrapper abandon(@PathVariable String id, @RequestAttribute LoginUser user) {
    roleService.abandon(id, user);
    return ResponseBodyWrapper.from("成功");
  }

  @ApiOperation("修改角色权限以外的信息.")
  @PutMapping(value = "{id}/info")
  @ApiLog("修改角色权限以外的信息")
  public ResponseBodyWrapper info(@PathVariable String id, @RequestBody Map map,
                                  @RequestAttribute LoginUser user) {
    roleService.editRoleInfo(id, map.get("name").toString(), user);
    return ResponseBodyWrapper.from("修改成功");
  }

  @ApiOperation("获取角色详情")
  @GetMapping(value = "{id}")
  @ApiLog("获取角色详情")
  public ResponseBodyWrapper getRoleDetails(@PathVariable String id) {
    RoleVo roleVO = roleMapStruct.dtoToVO(roleService.getRole(id));
    List<GetPageOutput> pagesDto = pageService.getPagesByRoleId(id);
    roleVO.setPermissions(pageMapStruct.pageDTOToRolePage(pagesDto));
    roleVO.setPages(pageService.getPageIdsByRoleId(id));
    roleVO.setOperations(pageService.getOperationsIdsByRoleId(id));
    return ResponseBodyWrapper.from(roleVO);
  }

}
