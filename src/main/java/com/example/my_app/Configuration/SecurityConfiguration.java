package com.example.my_app.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.my_app.Filter.AuthTokenFilter;
import com.example.my_app.modules.Auth.AdminDetailsService;
import com.example.my_app.modules.Auth.AuthEntryPointJwt;
import com.example.my_app.modules.Auth.CustomUserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        private AdminDetailsService adminDetailsService;

        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Bean
        public AuthTokenFilter authenticationJwtTokenFilter() {
                return new AuthTokenFilter();
        }

        @Bean
        public PasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager() {
                DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
                userProvider.setUserDetailsService(customUserDetailsService);
                userProvider.setPasswordEncoder(encoder());

                DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
                adminProvider.setUserDetailsService(adminDetailsService);
                adminProvider.setPasswordEncoder(encoder());

                return new ProviderManager(List.of(userProvider, adminProvider));
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/Private/**").authenticated()
                                                .requestMatchers("/Public/**").permitAll()
                                                .requestMatchers("/admin/**").permitAll()
                                                .anyRequest().authenticated())
                                .exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedHandler))
                                .oauth2Login(auth -> auth
                                                .loginPage("/oauth2/authorization/google")
                                                .defaultSuccessUrl("/Public/oauth2/redirect", true))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(authenticationJwtTokenFilter(),
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        public CorsFilter corsFilter() {
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(false);
                config.addAllowedOrigin("*");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                source.registerCorsConfiguration("/**", config);
                return new CorsFilter(source);
        }
}