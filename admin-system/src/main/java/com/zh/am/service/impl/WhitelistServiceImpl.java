package com.zh.am.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.am.domain.dao.WhitelistMapper;
import com.zh.am.domain.entity.Whitelist;
import com.zh.am.service.IWhitelistService;
import org.springframework.stereotype.Service;

@Service
public class WhitelistServiceImpl extends ServiceImpl<WhitelistMapper, Whitelist> implements IWhitelistService {

}
