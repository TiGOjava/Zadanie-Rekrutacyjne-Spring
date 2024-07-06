package com.RecruitmentTask.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @EnableWebSecurity
    public static class WebSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/register", "/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin((form) -> form
                            .loginPage("/login")
                            .defaultSuccessUrl("/home", true)
                            .permitAll()
                    )
                    .logout((logout) -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .permitAll()
                    );
            return http.build();
        }
    }
}