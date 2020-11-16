package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.domain.dao.OperationApiMapper;
import com.zh.am.domain.entity.OperationApi;
import com.zh.am.service.OperationApiService;
import org.springframework.stereotype.Service;

@Service
public class OperationApiServiceImpl extends ServiceImpl<OperationApiMapper, OperationApi> implements OperationApiService {

}
