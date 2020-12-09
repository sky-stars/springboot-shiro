package com.shiro.realms;

import com.shiro.entity.Account;
import com.shiro.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从UsernamePasswordToken中获取username
        String username = token.getUsername();
        // 调用数据库查询username对应的记录
        Account account = accountService.findByName(username);
        if (account != null) {
            return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();

        Set<String> roles = new HashSet<>();
        roles.add(account.getRoles());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置角色访问权限
        info.setRoles(roles);
        // 设置url访问权限
        info.addStringPermission(account.getPerms());

        return info;
    }

}
