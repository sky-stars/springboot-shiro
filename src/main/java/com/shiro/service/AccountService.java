package com.shiro.service;

import com.shiro.entity.Account;

public interface AccountService {
    public Account findByName(String name);
}
