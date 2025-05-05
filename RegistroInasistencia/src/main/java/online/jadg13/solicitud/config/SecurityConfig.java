package online.jadg13.solicitud.config;

import online.jadg13.solicitud.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF por ahora (para facilitar las pruebas con el frontend)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permitir acceso sin autenticación a las rutas de autenticación (login)
                        .requestMatchers("/api/estudiante/**").hasRole("ESTUDIANTE") // Ejemplo: solo estudiantes pueden acceder a /api/estudiante/**
                        .requestMatchers("/api/coordinador/**").hasRole("COORDINADOR") // Ejemplo: solo coordinadores pueden acceder a /api/coordinador/**
                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                )
                .authenticationProvider(authenticationProvider())
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login") // URL donde se enviarán las credenciales
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}