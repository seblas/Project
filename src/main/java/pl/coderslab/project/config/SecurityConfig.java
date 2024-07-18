package pl.coderslab.project.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DataSource dataSource;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/user", "/user/**").hasRole("USER") // dostęp wymaga uwierzytelnienia (dla zalogowanych) jako USER
                        .requestMatchers("/user", "/admin", "/admin/**").hasRole("ADMIN") // dostęp wymaga uwierzytelnienia (dla zalogowanych) jako ADMIN
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/") // tu jest formularz logowania
                        .loginProcessingUrl("/perform_login") // tu formularz logowania przesyła dane, SS sprawdza te dane
                        .defaultSuccessUrl("/user", true) // przekierowanie na /user po zalogowaniu
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout") // adres do wylogowania, SS sprawdza
                        .logoutSuccessUrl("/")
                        .permitAll()); // każdy ma dostęp do wylogowania

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT email AS username, password, TRUE as enabled FROM users WHERE email = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.email AS username, r.name AS authority FROM users u " +
                        "JOIN roles_users ru ON u.id = ru.users_id " +
                        "JOIN roles r ON ru.roles_id = r.id WHERE u.email = ?");
        return userDetailsManager;
    }

}
