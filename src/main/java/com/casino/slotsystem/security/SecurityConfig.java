package com.casino.slotsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .formLogin(f -> f.disable())
                .httpBasic(b -> b.disable())

                .authorizeHttpRequests(auth -> auth

                        // 🔥 PUBLIC TEST + SOCKETS
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/topic/**").permitAll()

                        // 🔥 PUBLIC USER APIs
                        .requestMatchers(HttpMethod.GET, "/api/slots/**").permitAll()
                        .requestMatchers("/api/admin/login").permitAll()

                        // 🔐 ADMIN APIs
                        .requestMatchers("/api/admin/**").authenticated()

                        .anyRequest().permitAll()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}


