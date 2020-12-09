package com.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shiro.entity.Account;
import com.shiro.mapper.AccountMapper;
import com.shiro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper mapper;
    @Override
    public Account findByName(String name){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);

    }
}
