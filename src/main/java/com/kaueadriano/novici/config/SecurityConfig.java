package com.kaueadriano.novici.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http// Desabilita CSRF, pois é uma API REST (sem formulários HTML tradicionais)
                .csrf(AbstractHttpConfigurer::disable)
                // Define quais rotas são públicas e quais exigem autenticação
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()  // login e registro são públicos
                        .requestMatchers("/h2-console/**").permitAll()  // libera o console do H2 (se estiver usando)
                        .anyRequest().authenticated()  // qualquer outra rota exige autenticação
                )
                // Necessário para o H2 Console funcionar corretamente com Spring Security
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // API stateless (sem sessão HTTP tradicional) — importante se for usar JWT depois
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Adiciona nosso filtro ANTES do filtro padrão de autenticação do Spring
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}