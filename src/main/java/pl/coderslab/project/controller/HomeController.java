package pl.coderslab.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.project.domain.User;

@Controller
public class HomeController {

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("user", new User());
        return "home/index";
    }
}
