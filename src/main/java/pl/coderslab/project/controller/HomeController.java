package pl.coderslab.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.project.domain.User;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home/index";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String test2() {
        return "admin";
    }


}
