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
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class MemberSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService memberUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(memberUserDetailsService)
            .passwordEncoder(passwordEncoder);
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
                    "/events/**",
                    "/cart/**",
                    "/productorder/**",
                    "/saveproduct/**")
            .and()
            .authorizeRequests()
            .antMatchers("/login",
                    "/loginPage",
                    "/register",
                    "/forgotPassword",
                    "/cart/items",
                    "/").permitAll()
            .antMatchers("/events/orders/**",
                    "/events/order/**",
                    "/events/*/tickets",
                    "/cart/**",
                    "/productorder/**",
                    "/saveproduct/**").hasRole("Member")
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
        failureHandler.setUseForward(false);
        return failureHandler;
    }
}
