package pl.coderslab.project.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @ResponseBody
    public String home() {
        return "User";
    }

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);





}
