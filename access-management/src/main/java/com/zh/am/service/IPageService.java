package com.zh.am.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.am.dto.page.GetPageOutput;
import com.zh.am.dto.page.UserPageDto;
import com.zh.am.entity.Page;

import java.util.List;

public interface IPageService extends IService<Page> {
  List<UserPageDto> getUserPages(String userId);

  List<String> getPageIdsByRoleId(String roleId);

  List<String> getOperationsIdsByRoleId(String roleId);

  /**
   * 获取角色详情API调用.
   *
   * @param roleId 角色Id
   * @return List PageDTO
   */
  List<GetPageOutput> getPagesByRoleId(String roleId);

  List<GetPageOutput> getPagesByMaxLevel(Integer maxLevel, String userId);

  List<GetPageOutput> getPageDetailsByPageId(String pageId, String userId);
}
