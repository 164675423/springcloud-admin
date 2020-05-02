package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.dao.PermissionMapper;
import com.zh.am.domain.entity.Permission;
import com.zh.am.service.IPermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
