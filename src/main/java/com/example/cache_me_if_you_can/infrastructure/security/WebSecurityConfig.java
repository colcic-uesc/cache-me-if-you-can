package com.example.cache_me_if_you_can.infrastructure.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final Environment env;
    private final JWTFilter jwtFilter;

    private final String[] USER_PATHS = new String[] { "/auth/login", "/auth/register" };
    private final String[] SWAGGER_PATHS = new String[] { "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html" };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] activeProfiles = env.getActiveProfiles();
        boolean isProduction = Arrays.asList(activeProfiles).contains("prod");

        http
                .csrf(csrf -> {
                    if (!isProduction) {
                        csrf.disable();
                    }
                })
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> {
                    if (!isProduction) {
                        requests.requestMatchers(SWAGGER_PATHS).permitAll();
                    }
                    requests.requestMatchers(HttpMethod.POST, USER_PATHS).permitAll();
                    requests.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}