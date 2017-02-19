package com.example.demo.Config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *
 * Created by jesse on 2017/2/19.
 */
@EnableRedisHttpSession//此注释创建一个springSessionRepositoryFilter Bean替换了HttpSession 用于SpringSession连接到Redis(6039默认)
public class HttpSessionConfig {

}
