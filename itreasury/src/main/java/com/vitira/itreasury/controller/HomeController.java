package com.vitira.itreasury.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<String> homePage() {
		return ResponseEntity.ok("Hello i-Treasury!");
	}

}