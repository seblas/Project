package pl.coderslab.project.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.project.domain.*;
import pl.coderslab.project.service.FieldService;
import pl.coderslab.project.service.RoleService;
import pl.coderslab.project.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/user")
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final FieldService fieldService;
    private static final Map<Integer, String> LEVELS = new LinkedHashMap<>() {{ // LinkedHashMap aby była zachowana kolejność
        put(1, "słaby");
        put(2, "średni");
        put(3, "dobry");
        put(4, "bardzo dobry");
        put(5, "zawodowiec");
    }};

    public UserController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleService roleService, FieldService fieldService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.fieldService = fieldService;
    }

    @RequestMapping("")
    public String home(Model model) {
        return "user/index";
    }

    @GetMapping(value = "/game")
    public String formGame(Model model) {
        model.addAttribute("game", new Game());
        List<Field> fields = fieldService.findAllSortedByStreet();
        model.addAttribute("fields", fields);
        model.addAttribute("levels", LEVELS);
        return "user/game";
    }

    @PostMapping("/game")
    public String Game(@Valid Game game, BindingResult result, Model model) {
        if(game.getStartTime()==null || game.getEndTime()==null || game.getRecruitmentEndTime()==null) {
            result.rejectValue("recruitmentEndTime", "error.game", "Wszystkie pola z datami muszą być wypełnione!");
        }
        else {
            if (game.getMaxLevel() < game.getMinLevel()) {
                result.rejectValue("minLevel", "error.game", "Minimalny poziom nie może być wyższy niż maksymalny!");
            }
            if (game.getStartTime().isAfter(game.getEndTime())) {
                result.rejectValue("startTime", "error.game", "Termin rozpoczęcia nie może być późniejszy niż termin zakończenia gry");
            }
            if (game.getStartTime().isBefore(game.getRecruitmentEndTime())) {
                result.rejectValue("recruitmentEndTime", "error.game", "Termin zgłaszania nie może być późniejszy niż termin rozpoczęcia gry");
            }
            if (LocalDateTime.now().isAfter(game.getRecruitmentEndTime())) {
                result.rejectValue("recruitmentEndTime", "error.game", "Termin zgłaszania już upłynął!");
            }
        }
        if(game.getCost()<0) {
            result.rejectValue("cost", "error.game", "Koszt gry nie może być ujemny!");
        }
        if(game.getMinAge()>game.getMaxAge()) {
            result.rejectValue("minAge", "error.game", "Minimalny wiek gracza nie może być większy od maksymalnego!");
        }

        if(game.getPlayersToFind()<0 || game.getPlayersToFind()>20) {
            result.rejectValue("playersToFind", "error.game", "Mozna szukać od 1 do 20 graczy!");
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.error("Validation error: {}", error));
            model.addAttribute("levels", LEVELS);
            List<Field> fields = fieldService.findAllSortedByStreet();
            model.addAttribute("fields", fields);
            return "user/game";
        }
        System.out.println("Dane poprawne");
        // Szukanie graczy
        List<User> users= userService.findByGame(game);

        System.out.println("Pobrana lista graczy");
        users.stream().forEach(System.out::println);
        return null;
    }

    @ModelAttribute("user")
    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
            Optional<User> user = userService.findByEmail(username);
            if(user.isPresent()) {
                return user.get();
            }
        }
        return null;
    }
}
