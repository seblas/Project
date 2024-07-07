package pl.coderslab.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "hello world !!!";
    }

    @RequestMapping("/2")
    public String home2AsFileJSP() {
        return "/index";
    }
}
