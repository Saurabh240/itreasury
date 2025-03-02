package com.vitira.itreasury.controller;

import com.vitira.itreasury.service.MT940ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MT940Controller {

    @Autowired
    private MT940ParserService mt940ParserService;

    @PostMapping("/parse")
    public String parseMT940(@RequestBody String mt940Message) {
    	mt940ParserService.parseAndSave(mt940Message);
        return "Message parsed and saved successfully!";
    }
}
