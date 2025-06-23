package br.univille.projetohotelpracachorro.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/login")
    public String showMyLoginPage() {
        // Retorna o nome do template Thymeleaf (sem a extensão .html)
        // O Spring Boot e o Thymeleaf vão procurar por src/main/resources/templates/login.html
        return "login";
    }

}
