package com.ezban.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@Order(1)
public class MemberSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService memberUserDetailsService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers()
                .antMatchers("/",
                             "/login",
                             "/loginPage",
                             "/register",
                             "/forgotPassword",
                             "/changePassword",
                             "/events/**")
                .and()
            .authorizeRequests()
                .antMatchers("/login",
                             "/loginPage",
                             "/register",
                             "/forgotPassword",
                             "/changePassword",
                             "/").permitAll()
                .antMatchers("/events/orders/**", "/events/order/**", "/events/*/tickets").hasRole("Member")
                .and()
            .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .successHandler(memberAuthenticationSuccessHandler())
                .failureHandler(memberAuthenticationFailureHandler())
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/loginPage")
                .and()
            .csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler memberAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/");
        };
    }

    @Bean
    public AuthenticationFailureHandler memberAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/loginPage?error=true");
        failureHandler.setUseForward(true); // 保留错误消息
        return failureHandler;
    }
}
