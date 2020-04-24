package com.example.pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/") // http get 으로 루트 요청시!
    public String welcome() {
        return "welcome"; // resources/templates/welcome.html
    }
}
