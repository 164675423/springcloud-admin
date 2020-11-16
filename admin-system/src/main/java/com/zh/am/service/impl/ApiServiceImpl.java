package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.domain.dao.ApiMapper;
import com.zh.am.domain.entity.Api;
import com.zh.am.service.ApiService;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

}
