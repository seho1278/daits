package com.springboot.daits.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public static final String[] PUBLIC_URL = {
            "/",
            "/api/member/signup",
            "/api/member/signin",
            "/api/post/{id}",
    };

    /**
     * WebSecurityConfigurerAdapter가 Spring Security 5.7.0-M2 부터 deprecated
     * component-based security configuration으로 전환
     * SecurityFilterChain에 Bean을 주입하여 사용
     */
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 모든 경로에 대한 액세스 허용
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeRequests(auth -> auth
                    .requestMatchers(PUBLIC_URL).permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public void configure(WebSecurity web) {
    }

}
