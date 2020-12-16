package com.shiro;

import com.shiro.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

    @RequestMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return url;
    }

    @RequestMapping("/login")
    public String redirect(String username, String password, Model model) {
        // 获取subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {

            // 记住我
            token.setRememberMe(true);
            // 登录验证
            subject.login(token);

            // 设置session
            Account account = (Account) subject.getPrincipal();
            subject.getSession().setAttribute("account", account);
            return "index";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }

    @RequestMapping("/unauth")
    @ResponseBody
    public String unauth() {
        return "未授权，无法访问！";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
