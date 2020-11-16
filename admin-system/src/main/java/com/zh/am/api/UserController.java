package com.zh.am.api;


import com.zh.am.domain.dto.page.PageDto;
import com.zh.am.domain.dto.user.GetUsersInput;
import com.zh.am.domain.dto.user.GetUsersOutput;
import com.zh.am.domain.dto.user.InsertUserDto;
import com.zh.am.domain.dto.user.UpdatePasswordDto;
import com.zh.am.domain.dto.user.UpdateUserDto;
import com.zh.am.service.OperationService;
import com.zh.am.service.PageService;
import com.zh.am.service.UserService;
import com.zh.common.context.LoginUser;
import com.zh.common.contract.ResponseBodyWrapper;
import com.zh.common.log.aspect.annotation.ApiLog;
import com.zh.common.page.PageRequest;
import com.zh.common.page.Paged;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author zh
 * @date 2020/1/8
 */
@Api(tags = "用户信息")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;
  private final OperationService operationService;
  private final PageService pageService;

  public UserController(UserService userService, OperationService operationService, PageService pageService) {
    this.userService = userService;
    this.operationService = operationService;
    this.pageService = pageService;
  }

  @ApiOperation("查询用户信息列表")
  @GetMapping()
  @ApiLog("查询用户信息列表")
  public ResponseBodyWrapper getUsers(GetUsersInput getUsersParam, PageRequest pageRequest) {
    Paged<GetUsersOutput> users = userService.getUsers(getUsersParam, pageRequest);
    return ResponseBodyWrapper.from(users);
  }

  @ApiOperation("查询用户信息详情")
  @GetMapping("/{id}")
  @ApiLog("查询用户信息详情")
  public ResponseBodyWrapper getUserById(@PathVariable String id) {
    return ResponseBodyWrapper.from(userService.getUsersById(id));
  }

  @ApiOperation("新增用户")
  @PostMapping()
  @ApiLog("新增用户")
  public ResponseBodyWrapper addUser(@RequestBody @Validated InsertUserDto input,
                                     @RequestAttribute(name = "user") LoginUser loginUser) {
    userService.insert(input, loginUser);
    return ResponseBodyWrapper.from("success");
  }

  @ApiOperation("修改用户信息")
  @PutMapping(value = "/{id}")
  @ApiLog("修改用户信息")
  public ResponseBodyWrapper updateUser(@PathVariable String id,
                                        @RequestBody @Validated UpdateUserDto input,
                                        @RequestAttribute(name = "user") LoginUser loginUser) {
    userService.update(id, input, loginUser);
    return ResponseBodyWrapper.from("成功");
  }

  @ApiOperation("作废用户信息")
  @PutMapping(value = "/{id}/abandon")
  @ApiLog("作废用户信息")
  public ResponseBodyWrapper abandonUser(@PathVariable String id,
                                         @RequestAttribute(name = "user") LoginUser loginUser) {
    userService.abandonUser(id, loginUser);
    return ResponseBodyWrapper.from("成功");
  }

  @ApiOperation("修改用户密码")
  @PutMapping(value = "/{id}/password/reset")
  @ApiLog("修改用户密码")
  public ResponseBodyWrapper resetPwd(@PathVariable String id,
                                      @RequestAttribute(name = "user") LoginUser loginUser) {
    userService.resetPwd(id, loginUser);
    return ResponseBodyWrapper.from("成功");
  }

  @ApiOperation("修改当前登录人员密码")
  @PutMapping(value = "/me/password")
  @ApiLog("修改当前登录人员密码")
  public ResponseBodyWrapper updatePassword(@RequestBody @Validated UpdatePasswordDto updatePasswordDto,
                                            @RequestAttribute(name = "user") LoginUser loginUser) {
    userService.updatePassword(updatePasswordDto, loginUser);
    return ResponseBodyWrapper.from("成功");
  }

  @ApiOperation("查询当前登录人的操作按钮")
  @GetMapping(value = "/me/operations")
  @ApiLog("查询当前登录人的操作按钮")
  public ResponseBodyWrapper getOperations(@RequestAttribute(name = "user") LoginUser loginUser) {
    return ResponseBodyWrapper.from(operationService.getOperationsByUserId(loginUser.getId()));
  }

  @ApiOperation("查询当前登录人的菜单")
  @GetMapping(value = "/me/pages")
  @ApiLog("查询当前登录人的操作按钮")
  public ResponseBodyWrapper getPages(@RequestAttribute(name = "user") LoginUser loginUser) {
    PageDto pageDto = new PageDto();
    pageDto.setPages(pageService.getUserPages(loginUser.getId()));
    return ResponseBodyWrapper.from(pageDto);
  }
}
