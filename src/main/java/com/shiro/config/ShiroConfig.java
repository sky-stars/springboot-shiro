package com.shiro.config;

import com.shiro.realms.AccountRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置页面访问权限
        Map<String, String> map = new Hashtable<>();
        // 无需授权
        map.put("/*", "authc");

        // authc需要登录授权
        map.put("/main", "authc");
        // 需要url访问权限
        map.put("/manager", "perms[manager]");
        // 需要角色权限
        map.put("/administrator", "roles[administrator]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        // 设置登录返回页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 设置未授权提示
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(accountRealm);
        return manager;
    }


    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 加密次数
        credentialsMatcher.setHashIterations(1);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public AccountRealm accountRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        AccountRealm authRealm = new AccountRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

}
