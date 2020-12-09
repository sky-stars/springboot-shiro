package com.shiro.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Test
    void find() {
        System.out.println(accountService.findByName("yang"));
    }

}