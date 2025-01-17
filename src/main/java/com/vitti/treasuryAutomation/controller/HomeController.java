package com.vitti.treasuryAutomation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> homePage() {
    	return ResponseEntity.ok("Hello World!");
    }

}