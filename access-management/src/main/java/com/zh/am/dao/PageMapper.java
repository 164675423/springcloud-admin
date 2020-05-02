package com.zh.am.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.am.domain.entity.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageMapper extends BaseMapper<Page> {
  List<Page> getPagesByUserId(@Param("userId") String userId);

  List<Page> getPageByRole(@Param("roleId") String roleId);
}
