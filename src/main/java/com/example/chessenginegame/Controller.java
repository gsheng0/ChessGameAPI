package com.example.chessenginegame;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class Controller {
    @GetMapping("/")
    public String home(){
        return "CHESS API HOME";
    }
}
