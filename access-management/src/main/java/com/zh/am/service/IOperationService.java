package com.zh.am.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.am.dto.operation.GetUserOperationsDto;
import com.zh.am.entity.Operation;

import java.util.List;

public interface IOperationService extends IService<Operation> {
  List<GetUserOperationsDto> getOperationsByUserId(String userId);
}
