package com.skhu.mid_skhu.global.config;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("localstack")
@EnableWebSecurity
public class ActuatorSecurityConfig {

    @Bean
    SecurityFilterChain actuatorOpen(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher("/actuator/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }
}
