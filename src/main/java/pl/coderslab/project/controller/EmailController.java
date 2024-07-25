package pl.coderslab.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.project.service.EmailService;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-test-email")
    @ResponseBody
    public String sendTestEmail() {
        String body = "Kliknij: \n http://localhost:8080?costam";
        emailService.sendEmail("seblas@tlen.pl", "Tytuł", body);
        return "Email sent successfully";
    }
}
