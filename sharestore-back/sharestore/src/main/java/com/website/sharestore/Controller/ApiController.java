package com.website.sharestore.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {  
        return ResponseEntity.ok("test");
    }
}