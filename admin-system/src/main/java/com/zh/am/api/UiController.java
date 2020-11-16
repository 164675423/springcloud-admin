package com.zh.am.api;

import com.zh.am.service.RoleService;
import com.zh.common.contract.ResponseBodyWrapper;
import com.zh.common.log.aspect.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供UI所需api
 *
 * @author zh
 * @date 2020/3/1
 */
@Api(tags = "UI")
@RestController
@RequestMapping("api/v1/ui")
public class UiController {
  private final RoleService roleService;

  public UiController(RoleService roleService) {
    this.roleService = roleService;
  }

  @ApiOperation("用户信息管理初始化")
  @GetMapping(value = "user/init")
  @ApiLog("用户信息管理初始化")
  public ResponseBodyWrapper getUserInit() {
    return ResponseBodyWrapper.from(roleService.getRoleIdName());
  }
}
