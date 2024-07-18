package pl.coderslab.project.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.project.domain.Address;
import pl.coderslab.project.domain.Role;
import pl.coderslab.project.domain.User;
import pl.coderslab.project.service.RoleService;
import pl.coderslab.project.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
public class HomeController {

    private static final Map<Integer, String> LEVELS = new LinkedHashMap<>() {{ // LinkedHashMap aby była zachowana kolejność
        put(1, "słaby");
        put(2, "średni");
        put(3, "dobry");
        put(4, "bardzo dobry");
        put(5, "zawodowiec");
    }};

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private static final String REGISTRATION_SUCCESS_MESSAGE = "Konto zostało załozone, zaloguj się";

    public HomeController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/")
    public String home() {
        return "home/index";
    }

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new User(new Address()));
        model.addAttribute("levels", LEVELS);
        return "home/registration";
    }

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @PostMapping("/registration")
    public String registration(@Valid User user, BindingResult result, Model model) {
        if(user.getPassword().length()<5 || user.getPassword().length()>20) {
            result.rejectValue("password", "error.user", "Hasło musi mieć od 5 do 20 znaków!");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Hasła nie są zgodne");
        }
        if(!user.getPassword().matches(".*\\d.*")) {
            result.rejectValue("password", "error.user", "Hasło musi zawierać przynajmniej jedną cyfrę");
        }
        if(!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z]).+$")) {
            result.rejectValue("password", "error.user", "Hasło musi zawierać przynajmniej jedną małą i dużą literę");
        }
        if(!user.getPassword().matches(".*[\\W_].*")) {
            result.rejectValue("password", "error.user", "Hasło musi zawierać przynajmniej jeden znak specjalny");
        }
        if(user.getDateOfBirth() == null) {
            result.rejectValue("dateOfBirth", "error.user", "Musisz podać datę urodzenia!");
        } else if(!checkAge(user.getDateOfBirth())) {
            result.rejectValue("dateOfBirth", "error.user", "Musisz mieć minimum 16 lat");
        }
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.error("Validation error: {}", error));
            model.addAttribute("levels", LEVELS);
            return "home/registration";
        }

        // Sprawdzanie, czy adres jest pusty
        if (user.getAddress() != null && user.getAddress().isEmpty()) {
            user.setAddress(null);
        }

        // kodowanie hasła
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleService.findByName("ROLE_USER");
        if (role.isPresent()) {
            user.addRole(role.get());
            userService.saveUser(user);
            logger.info("Użytkownik zarejestrowany z rolą 'ROLE_USER': {}", user.getEmail());
        } else {
            throw new RuntimeException("Role 'ROLE_USER' not found");
        }

        model.addAttribute("registrationSuccessMessage", REGISTRATION_SUCCESS_MESSAGE);
        return "home/index";
    }

    private boolean checkAge(LocalDate localDate) {
        Period period = Period.between(localDate, LocalDate.now());
        return period.getYears() >= 16;
    }


}
