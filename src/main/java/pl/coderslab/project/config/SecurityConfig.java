package pl.coderslab.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/blad", "/admin").permitAll() // dostęp nie wymaga uwierzytelnienia (dla wszystkich)
                        .requestMatchers("/dashboard", "/dashboard/**").hasRole("USER") // dostęp wymaga uwierzytelnienia (dla zalogowanych) jako USER
                        .requestMatchers("/admin/**").hasRole("ADMIN") // dostęp wymaga uwierzytelnienia (dla zalogowanych) jako ADMIN
                )
                .formLogin(form -> form
                        .loginPage("/") // strona logowania znajduje się pod adresem "/"
                        .loginProcessingUrl("/perform_login") // URL do przetwarzania logowania, unikalny URL
                        .defaultSuccessUrl("/dashboard", true) // przekierowanie na /dashboard po zalogowaniu
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/")
                        .permitAll()); // każdy ma dostęp do wylogowania

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT email AS username, password FROM users WHERE email = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.email AS username, r.name AS authority FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?");
        return userDetailsManager;
    }
}
