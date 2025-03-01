// package com.vitira.itreasury.controller;

// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;

// import javax.servlet.http.HttpServletRequest;

// @Controller
// public class CustomErrorController implements ErrorController {

//     @RequestMapping("/error")
//     public String handleError(HttpServletRequest request) {
//         // You can add custom logic here to handle different error codes
//         return "error"; // Return the name of the error view
//     }

//     @Override
//     public String getErrorPath() {
//         return "/error";
//     }
// }