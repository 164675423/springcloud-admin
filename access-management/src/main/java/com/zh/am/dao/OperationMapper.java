package com.zh.am.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.am.domain.dto.operation.GetUserOperationsDto;
import com.zh.am.domain.entity.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationMapper extends BaseMapper<Operation> {
  List<GetUserOperationsDto> getUserOperations(@Param("userId") String userId);

  List<Operation> getOperationByRole(@Param("roleId") String roleId);
}
