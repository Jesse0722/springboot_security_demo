package com.example.demo.Config;



import com.example.demo.handlers.LoginSuccessHandler;
import com.example.demo.services.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * Created by jesse on 2017/2/17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //注册CustomUserDetailsService的Bean
    @Bean
    CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }

    @Autowired
    private DataSource dataSource;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("jesse").password("password").roles("USER").build());
//        manager.createUser(User.withUsername("xieweiba").password("password").roles("USER","ADMIN").build());
//        return manager;
//    }

    /***
     * 添加自定的user detail service认证，在service中进行了数据库密码校验认证
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService());
//    }

    /***
     * http请求配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            authorizeRequests()  //authorizeRequests定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/","/home").permitAll()  //指定 / 和 /home 不需要任何认证就可以访问
                .anyRequest().authenticated()  //其他的路径都必须通过身份验证。
                .and()
            .formLogin()    //需要登录
                .loginPage("/login")  //需要登录到跳转页面
                .permitAll()   //登录页面每个人都可以访问
                .successHandler(loginSuccessHandler())
                .and()
            .logout()
                .logoutSuccessUrl("/home") //登出后的默认网址
                .permitAll() //登出页面每个人都可以访问
                .invalidateHttpSession(true) //销毁session
                .and()
            .rememberMe() //登录后记住用户下次自动登录
                .tokenValiditySeconds(1209600) //token有效时间
                .tokenRepository(tokenRepository());//制定记住登录信息所使用的数据源
              //  .deleteCookies()
    }
    /***
     * 全局配置
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
        auth
            .eraseCredentials(false);//不要删除凭证，以便记住用户
    }

    //给密码加密
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }
}
