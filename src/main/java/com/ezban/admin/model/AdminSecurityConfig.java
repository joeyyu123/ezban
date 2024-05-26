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
@Order(2)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService adminUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 加密
        // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(adminUserDetailsService) // 使用管理员的用户详情服务
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers( "/api/**","/qaback/**","/adminlogin","/admin/login","/adminlogout", "/adminregister", "/adminpasswordreset","/adminmanage/**","/admin/**")
                .and()
            .authorizeRequests()
                .antMatchers("/api/**","/qaback/**","/adminlogin", "/adminregister", "/adminpasswordreset","/admin/login").permitAll()
                .antMatchers("/adminmanage/**","/admin/productorder/selectPage","/admin/productreport","/admin/productorder/listAllProductOrder","/admin/productorder/listOneProductOrder/**","/admin/productorder/update/**","/birthdayback").hasRole("ADMIN") // 仅允许管理员访问管理界面
                .and()
            .formLogin()
                .loginPage("/adminlogin")
                .loginProcessingUrl("/admin/login")
                .successHandler(adminAuthenticationSuccessHandler())
                .failureHandler(adminAuthenticationFailureHandler())
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/adminlogout") // 确保登出URL正确
                .logoutSuccessUrl("/adminlogin")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/adminlogin")
                .and()
            .csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler adminAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/adminmanage"); // 重定向到管理员管理界面
        };
    }

    @Bean
    public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/adminlogin?error=true");
        return failureHandler;
    }
}
