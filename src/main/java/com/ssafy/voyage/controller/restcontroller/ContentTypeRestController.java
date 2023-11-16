package com.ssafy.voyage.controller.restcontroller;

import com.ssafy.voyage.service.ContentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content-type")
@RequiredArgsConstructor
public class ContentTypeRestController {
    private final ContentTypeService contentTypeService;

    @GetMapping
    public ResponseEntity findContentType() {
        return ResponseEntity.ok(
            contentTypeService.findAll()
        );
    }
}
