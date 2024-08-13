package njala.st.pj.healthinsurance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/app/login")
    public String login() {
        return "app/applogin";
    }
    @GetMapping("/app/home")
    public String home() {
        return "app/home";
    }
    @GetMapping("/error")
    public String error() {
        return "app/error";
    }
}

