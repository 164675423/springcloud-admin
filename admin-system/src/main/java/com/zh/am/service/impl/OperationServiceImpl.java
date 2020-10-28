package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.domain.dao.OperationMapper;
import com.zh.am.domain.dto.operation.GetUserOperationsDto;
import com.zh.am.domain.entity.Operation;
import com.zh.am.service.IOperationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements IOperationService {
  private final OperationMapper operationMapper;

  public OperationServiceImpl(OperationMapper operationMapper) {
    this.operationMapper = operationMapper;
  }

  @Override
  public List<GetUserOperationsDto> getOperationsByUserId(String userId) {
    return operationMapper.getUserOperations(userId);
  }
}
