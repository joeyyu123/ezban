package com.ezban.host.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
public class HostSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService hostUserDetailsService;

    @Bean
    public PasswordEncoder hostPasswordEncoder() {
        // 加密
        // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(hostUserDetailsService) // 增加加密
            .passwordEncoder(hostPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/hostlogin", "/hostregister", "/hostpasswordreset").permitAll()
                .antMatchers("/host/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/hostlogin")
                .loginProcessingUrl("/host/login")
                .successHandler(hostAuthenticationSuccessHandler())
                .failureHandler(hostAuthenticationFailureHandler())
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/host/logout")
                .logoutSuccessUrl("/hostlogin")
                .and()
            .csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler hostAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/backhost");
        };
    }

    @Bean
    public AuthenticationFailureHandler hostAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/hostlogin?error=true");
        failureHandler.setUseForward(false);
        return failureHandler;
    }
}
