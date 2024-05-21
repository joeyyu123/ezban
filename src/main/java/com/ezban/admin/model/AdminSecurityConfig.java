package com.ezban.admin.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
@Order(3)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService adminUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(adminUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/adminlogin", "/adminregister", "/adminpasswordreset").permitAll() // 确保登录页面路径正确
                .antMatchers("backstage/adminmanage/*").authenticated()
                .and()
            .formLogin()
                .loginPage("/adminlogin") // 自定义登录页面
                .loginProcessingUrl("/adminlogin") // 表单提交的URL
                .successHandler(adminAuthenticationSuccessHandler())
                .failureHandler(adminAuthenticationFailureHandler())
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/adminlogin")
                .permitAll()
                .and()
            .csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler adminAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            Logger logger = LoggerFactory.getLogger(AdminSecurityConfig.class);
            logger.info("登录成功: 用户名=" + authentication.getName());
            response.sendRedirect("/adminmanage"); // 登录成功后重定向到/adminmanage
        };
    }

    @Bean
    public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/adminlogin.html?error=true");
        failureHandler.setUseForward(false);

        return (request, response, exception) -> {
            Logger logger = LoggerFactory.getLogger(AdminSecurityConfig.class);
            logger.error("登录失败: " + exception.getMessage());
            failureHandler.onAuthenticationFailure(request, response, exception);
        };
    }
}
