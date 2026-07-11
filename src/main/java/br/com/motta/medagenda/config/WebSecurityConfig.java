package br.com.motta.medagenda.config;

import br.com.motta.medagenda.security.CustomAuthenticationEntryPoint;
import br.com.motta.medagenda.security.SecurityFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = WebSecurityConfig.SECURITY, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class WebSecurityConfig {

    public static final String SECURITY = "bearerAuth";

    private final SecurityFilter securityFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public WebSecurityConfig(SecurityFilter securityFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.securityFilter = securityFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/medicos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/medicos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/medicos/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/medicos/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pacientes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/pacientes/{id}").hasAnyRole("PACIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pacientes/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/especialidades").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/especialidades/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/especialidades/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/especialidades/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/agenda").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/agenda/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/agenda/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/agenda/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/medico-especialidades").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/medico-especialidades/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/medico-especialidades/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/consultas").authenticated()
                        .requestMatchers(HttpMethod.GET, "/consultas/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/consultas/{id}/confirmar").hasAnyRole("ADMIN", "PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/consultas/{id}/cancelar").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/consultas/{id}/realizada").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.PUT, "/consultas/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/consultas/{id}").authenticated()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
