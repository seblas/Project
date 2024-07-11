package pl.coderslab.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    @ResponseBody
    public String dashboard() {
        return "Tu ma być dashboard";
        //return "dashboard/index"; // zakładając, że plik JSP znajduje się w katalogu WEB-INF/views/dashboard/
    }
}
