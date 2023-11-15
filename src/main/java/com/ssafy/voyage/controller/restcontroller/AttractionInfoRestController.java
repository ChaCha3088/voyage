package com.ssafy.voyage.controller.restcontroller;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.dto.response.Response;
import com.ssafy.voyage.message.cause.Causes;
import com.ssafy.voyage.service.AttractionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ssafy.voyage.message.message.Messages.REQUEST_VALIDATION;
import static com.ssafy.voyage.message.message.Messages.INVALID;

@RestController
@RequestMapping("/api/attraction-info")
@RequiredArgsConstructor
public class AttractionInfoRestController {
    private final AttractionInfoService attractionInfoService;

    @GetMapping
    public ResponseEntity findAttractionInfoNoOffset(@Valid @RequestBody AttractionInfoRequestDto attractionInfoRequestDto) {
        return ResponseEntity.ok()
            .body(attractionInfoService.findAttractionInfoDtoNoOffset(attractionInfoRequestDto));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException() {
        return ResponseEntity.badRequest()
            .body(
                Response.builder()
                    .cause(Causes.REQUEST_VALIDATION.getMessage())
                    .message(new StringBuffer().append(REQUEST_VALIDATION.getMessage()).append(INVALID.getMessage()).toString())
                    .build()
            );
    }
}
