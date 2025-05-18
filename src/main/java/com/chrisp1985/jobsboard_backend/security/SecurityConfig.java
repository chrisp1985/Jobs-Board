package com.chrisp1985.jobsboard_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/jobs/**").authenticated() // allow any authenticated user
                        .requestMatchers(HttpMethod.POST, "/jobs/**").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT, "/jobs/**").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/jobs/**").hasRole("admin")
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}