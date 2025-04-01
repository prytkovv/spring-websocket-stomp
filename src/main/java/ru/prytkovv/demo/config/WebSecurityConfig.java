package ru.prytkovv.demo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/error").permitAll()
                                .anyRequest().authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/api/login")
                                .usernameParameter("user")
                                .passwordParameter("pass")
                                .successHandler((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.getWriter().write("{\"status\":\"ok\"}");
                                })
                                .failureHandler(new ExceptionMappingAuthenticationFailureHandler())
                                .permitAll()
                )
                .csrf((csrf) -> csrf.disable())
                .build();
    }
}
