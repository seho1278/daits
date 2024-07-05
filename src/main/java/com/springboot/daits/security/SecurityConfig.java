package com.springboot.daits.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * WebSecurityConfigurerAdapter가 Spring Security 5.7.0-M2 부터 deprecated
     * component-based security configuration으로 전환
     * SecurityFilterChain에 Bean을 주입하여 사용
     */
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 모든 경로에 대한 액세스 허용
        http
            .csrf().disable()
            .authorizeRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

}