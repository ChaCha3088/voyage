package com.ssafy.voyage.controller.restcontroller;

import com.ssafy.voyage.service.SidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sido", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SidoRestController {
    private final SidoService sidoService;

    @GetMapping
    public ResponseEntity findSido() {
        return ResponseEntity.ok(
            sidoService.findAll()
        );
    }
}
