package br.com.caslulu.academia_api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(req -> {
                req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();

                req.requestMatchers(HttpMethod.POST, "/alunos").permitAll();
                req.requestMatchers(HttpMethod.PUT, "/alunos/**").hasAuthority("ROLE_ADMIN");
                req.requestMatchers(HttpMethod.DELETE, "/alunos/**").hasAuthority("ROLE_ADMIN");
                
                req.requestMatchers(HttpMethod.POST, "/aulas").hasAuthority("ROLE_ADMIN");
                req.requestMatchers(HttpMethod.PUT, "/aulas/**").hasAuthority("ROLE_ADMIN");
                req.requestMatchers(HttpMethod.DELETE, "/aulas/**").hasAuthority("ROLE_ADMIN");
          
                req.requestMatchers(HttpMethod.POST, "/inscricoes").hasAuthority("ROLE_ALUNO");
                req.requestMatchers(HttpMethod.POST, "/inscricoes/**").hasAuthority("ROLE_ALUNO");
                req.requestMatchers(HttpMethod.GET, "/inscricoes/aluno/**").hasAuthority("ROLE_ALUNO");
                req.requestMatchers(HttpMethod.GET, "/inscricoes/aula/**").hasAuthority("ROLE_ADMIN");

                req.anyRequest().authenticated();
            })
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
