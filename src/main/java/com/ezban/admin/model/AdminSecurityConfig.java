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

    // 使用不進行任何操作的密碼編碼器，這在實際應用中是不安全的，僅適用於開發階段
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // 配置身份認證管理，指定用戶詳情服務和密碼編碼器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(adminUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    // 配置HTTP安全規則
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/adminlogin", "/adminregister", "/adminpasswordreset").permitAll() // 允許所有用戶訪問登錄、註冊和重置密碼頁面
                .antMatchers("/adminmanage/*").authenticated() // 只有認證用戶能訪問管理後台
                .and()
            .formLogin()
                .loginPage("/adminlogin") // 指定自定義登錄頁面的URL
                .loginProcessingUrl("/adminlogin") // 指定處理登錄請求的URL
                .successHandler(adminAuthenticationSuccessHandler()) // 登錄成功處理器
                .failureHandler(adminAuthenticationFailureHandler()) // 登錄失敗處理器
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/adminlogin") // 登出後重定向到登錄頁面
                .permitAll()
                .and()
            .csrf().disable(); // 禁用CSRF保護
    }

    // 登錄成功處理器
    @Bean
    public AuthenticationSuccessHandler adminAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            Logger logger = LoggerFactory.getLogger(AdminSecurityConfig.class);
            logger.info("登录成功: 用户名=" + authentication.getName());
            response.sendRedirect("/adminmanage"); // 登录成功后重定向到管理页面
        };
    }

    // 登錄失敗處理器
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
