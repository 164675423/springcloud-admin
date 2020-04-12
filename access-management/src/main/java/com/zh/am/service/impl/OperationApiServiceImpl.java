package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.dao.OperationApiMapper;
import com.zh.am.entity.OperationApi;
import com.zh.am.service.IOperationApiService;
import org.springframework.stereotype.Service;

@Service
public class OperationApiServiceImpl extends ServiceImpl<OperationApiMapper, OperationApi> implements IOperationApiService {

}
