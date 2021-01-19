package soa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="")
@CrossOrigin(allowCredentials = "true")
public class Test {
    @GetMapping(value = "/")
    private ResponseEntity<String> testServer() {
        return ResponseEntity.ok("Сервер работает!");
    }
}