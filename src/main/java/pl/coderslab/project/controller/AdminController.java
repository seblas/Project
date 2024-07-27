package pl.coderslab.project.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.project.domain.*;
import pl.coderslab.project.service.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/admin")
@Controller
@Secured({"ROLE_ADMIN"})
public class AdminController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final FieldService fieldService;

    private final InvitationService invitationService;
    private final GameService gameService;
    private final EmailService emailService;

    public AdminController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleService roleService, FieldService fieldService, InvitationService invitationService, GameService gameService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.fieldService = fieldService;
        this.invitationService = invitationService;
        this.gameService = gameService;
        this.emailService = emailService;
    }

    @RequestMapping("")
    public String home(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        return "admin/index";
    }

    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @RequestMapping("/user/admin/{id}")
    public String changeAdmin(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<Role> roles = user.getRoles();
            Optional<Role> optionalRole = roleService.findByName("ROLE_ADMIN");

            if (optionalRole.isPresent()) {
                Role adminRole = optionalRole.get();
                if (roles.contains(adminRole)) {
                    roles.remove(adminRole);
                } else {
                    roles.add(adminRole);
                }
                userService.saveUser(user);
            } else {
                throw new RuntimeException("Role 'ROLE_ADMIN' not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
        return "redirect:/admin/users";
    }

    @RequestMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);
        optionalUser.ifPresent(userService::deleteUser);
        return "redirect:/admin/users";
    }

}
