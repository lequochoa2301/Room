package com.example.Room.configuration;

import com.example.Room.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("").permitAll() // Ai cũng có thể truy cập
                        .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR") // Chỉ cho phép ADMIN và CREATOR
                        .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR") // Chỉ cho phép ADMIN và EDITOR
                        .antMatchers("/delete/**").hasAuthority("ADMIN") // Chỉ ADMIN có thể xóa
                        .anyRequest().authenticated() // Tất cả các yêu cầu khác phải xác thực
                )
                .formLogin(form -> form
                        .permitAll() // Cho phép tất cả mọi người truy cập trang đăng nhập
                )
                .logout(logout -> logout
                        .permitAll() // Cho phép tất cả mọi người đăng xuất
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403") // Trang truy cập bị từ chối
                );

        return http.build();
    }

    @Bean
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
