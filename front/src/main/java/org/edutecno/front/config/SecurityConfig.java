package org.edutecno.front.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerBuilder auth;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/auth/**", "/css/**", "/js/**", "/webjars/**").permitAll()
                        //TODO permitir el paso solo con el logeo con el token ver como applicarlo
                        .requestMatchers("/alumnos/**").permitAll()
                        //.requestMatchers("/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/client/**").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("email")    // Especifica el nombre del campo email
                        .passwordParameter("password") // Especifica el nombre del campo password
                        .loginProcessingUrl("/auth/login")  // URL donde se enviará el formulario
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }
}

