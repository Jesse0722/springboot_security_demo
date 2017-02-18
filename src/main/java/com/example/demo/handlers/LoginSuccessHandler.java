package com.example.demo.handlers;


import com.example.demo.domain.SUser;
import com.example.demo.services.SUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功的handler
 * 可以在这里将用户dengue信息存入数据库
 * Created by jesse on 2017/2/18.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final Logger log = LoggerFactory.getLogger(SUserService.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SUser userDetails=null;
        if(authentication.isAuthenticated()){
            //获得授权后可得到用户信息   可使用SUserService进行数据库操作
             userDetails = (SUser)authentication.getPrincipal();
        }

        log.info("Username:"+userDetails.getName()+"登录");
        log.info("IP:"+ getIpAddress(request));
        super.onAuthenticationSuccess(request,response,authentication);
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip==null||ip.length()==0||"unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip==null||ip.length()==0||"unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip==null||ip.length()==0||"unknow".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
