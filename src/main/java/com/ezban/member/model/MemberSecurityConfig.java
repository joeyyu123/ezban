package com.ezban.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@Configuration
@EnableWebSecurity
@Order(1)
public class MemberSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberUserDetailsService memberUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(memberUserDetailsService)
                .passwordEncoder(memberPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/",
                        "/login",
                        "/logout",
                        "/loginPage",
                        "/register",
                        "/forgotPassword",
                        "/auth/status",
                        "/events/**",
                        "/saveevent/**",
                        "/frontstage/memberchat/**",
                        "/frontstage/qrcodeticket/**",
                        "/cart/**",
                        "/product/**",
                        "/productorder/**",
                        "/productorderdetail/**",
                        "/productreport/**",
                        "/saveproduct/**",
                        "/eventCommentsReview/**",
                        "/members/**",
                        "/qa")
                .and()

                .authorizeRequests()
                .antMatchers("/login",
                        "loginPage",
                        "/register",
                        "/forgotPassword",
                        "/auth/status",
                        "/cart/items",
                        "/",
                        "/product/**",
                        "/productorder/**",
                        "/productorderdetail/**",
                        "/productreport/**",
                        "/saveproduct/**").permitAll()
                .antMatchers("/events/orders/**",
                        "/events/order/**",
                        "/events/*/tickets",
                        "/cart/**",
                        "/productorder/**",
                        "/productorderdetail/**",
                        "/productreport/**",
                        "/saveproduct/**",
                        "/saveevent/**",
                        "/frontstage/memberchat/**",
                        "/frontstage/qrcodeticket/**",
                        "/eventCommentsReview/**",
                        "/members/**").hasRole("Member")
                .and()

                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .successHandler(memberAuthenticationSuccessHandler())
                .failureHandler(memberAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // 确保登出URL正确
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/loginPage")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder memberPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();

//        return new BCryptPasswordEncoder(); // 加密要這行
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

    // 用於驗證Ajax中"商品收藏"與"商品檢舉"的會員登入狀態判斷
    @RestController
    @RequestMapping("/auth")
    public class AuthController {
        @GetMapping("/status")
        public ResponseEntity<Boolean> checkAuthStatus(Principal principal) {
            return ResponseEntity.ok(principal != null);
        }
    }

}

