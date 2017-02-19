package com.example.demo.controllers;


import com.example.demo.domain.SRole;
import com.example.demo.domain.SUser;
import com.example.demo.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jesse on 2017/2/19.
 */
@Controller
@RequestMapping("/")
public class GreetingController {





    @RequestMapping("/home")
    public String home() {
//        SUser user = new SUser();
//        user.setName("lijiajun");
//        BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
//        user.setPassword(bc.encode("111111"));
//        user.setEmail("xieweiba@gmail.com");

//        sUserService.create(user);
        return "home";
    }

//    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }

//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public String loginIn(Model model){
//
//    }

    @RequestMapping("/hello")
    public String hello() {
        SecurityContext ctx   =   SecurityContextHolder.getContext();
        Authentication auth   =   ctx.getAuthentication();
        if(auth.getPrincipal()   instanceof UserDetails)
        {
            SUser user   =   (SUser)auth.getPrincipal();
            System.out.println(user.getEmail());
        }
        //本段代码演示如何获取登录的用户资料

        return "hello";
    }

    @RequestMapping("/")
    public String root() {
        //如不进行此项配置，从login登录成功后，会提示找不网页
        return "index";
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

}
