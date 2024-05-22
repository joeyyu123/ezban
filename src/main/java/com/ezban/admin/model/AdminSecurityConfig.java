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
    private AdminUserDetailsService adminUserDetailsService;

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
                .antMatchers("/admin/login", "/adminregister", "/adminpasswordreset").permitAll()
                .antMatchers("/adminmanage/*").authenticated()
                .and()
            .formLogin()
                .loginPage("/adminlogin") // 自定義登錄頁面的URL
                .loginProcessingUrl("/admin/login") // 處理登錄請求的URL
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
            response.sendRedirect("/adminmanage");
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

