package com.example.demo.services.security;


import com.example.demo.domain.SUser;
import com.example.demo.domain.SecurityUser;
import com.example.demo.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 该类是用户信息的定义和验证
 * 这个类主要是处理用户登录信息，在用户输入用户名和密码后，
 * spring security会带着用户名调用类里面的loadUserByUsername(usrename)方法，
 * 通过用户名查出用户信息，然后把数据库中查出的用户密码和刚刚用户输入的存储在session中的密码做比较，然后判断该用户是否合法！
 * Created by jesse on 2017/2/18.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //数据库服务类
    private SUserService suserService;//code7

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //SUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        //本例使用SUser中的email作为用户名:
        SUser user = suserService.findByName(userName); //code8
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        // SecurityUser实现UserDetails并将SUser的Email映射为username
        return new SecurityUser(user); //code9
    }
}

