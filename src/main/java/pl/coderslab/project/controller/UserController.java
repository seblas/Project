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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.project.domain.Address;
import pl.coderslab.project.domain.Role;
import pl.coderslab.project.domain.User;
import pl.coderslab.project.service.RoleService;
import pl.coderslab.project.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@RequestMapping("/user")
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("user", getLoggedUser());
        return "user/index";
    }

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("userDetails: " + userDetails);
            username = userDetails.getUsername();
            System.out.println("userName: " + username);
            Optional<User> user = userService.findByEmail(username);
            if(user.isPresent()) {
                System.out.println("znaleziony user: " + user.get());
                return user.get();
            }
        }
        return null;
    }

}
