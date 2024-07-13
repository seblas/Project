package pl.coderslab.project.testing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "seb"; // zmień na swoje hasło
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("seb: " + encodedPassword);

        System.out.println("password: " + passwordEncoder.encode("password"));

        rawPassword = "haslo"; // zmień na swoje hasło
        encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("haslo: " + encodedPassword);
    }
}
