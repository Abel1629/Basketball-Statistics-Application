package com.statsproject.statsproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // allow H2 console
                )

                // allow H2 console to be displayed in a frame
                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                )

                .authorizeHttpRequests(auth -> auth
                        // Public pages
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/login").permitAll()

                        // ROLE RESTRICTIONS
                        .requestMatchers("/teams/**", "/games/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/players/**", "/boxscores/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")


                        // EVERYTHING ELSE (including "/") requires login
                        .anyRequest().authenticated()
                )


                // default login page enabled
                .formLogin(form -> form
                        .loginPage("/login")       // your custom login page
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
