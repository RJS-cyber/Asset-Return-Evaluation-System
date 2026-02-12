package com.ares.backend;

import com.ares.backend.dto.responseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ares")
public class Controller {
/*
    private final Service service;

    public Controller(Service service) {this.service = service;}

    @PostMapping("/calculate")
    public ResponseEntity<responseData> calculate(RequestBody sendData data){
        try {
            return ResponseEntity.ok(service.calculate(data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
 */
}
