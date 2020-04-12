package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.dao.ApiMapper;
import com.zh.am.entity.Api;
import com.zh.am.service.IApiService;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements IApiService {

}
