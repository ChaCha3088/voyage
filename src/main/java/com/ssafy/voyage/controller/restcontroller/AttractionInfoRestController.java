package com.ssafy.voyage.controller.restcontroller;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.dto.response.Response;
import com.ssafy.voyage.exception.NoSuchAttractionInfoException;
import com.ssafy.voyage.message.cause.AttractionCause;
import com.ssafy.voyage.message.cause.Causes;
import com.ssafy.voyage.service.AttractionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.ssafy.voyage.message.cause.AttractionCause.ATTRACTION;
import static com.ssafy.voyage.message.message.Messages.REQUEST_VALIDATION;
import static com.ssafy.voyage.message.message.Messages.INVALID;

@RestController
@RequestMapping("/api/attraction")
@RequiredArgsConstructor
public class AttractionInfoRestController {
    private final AttractionInfoService attractionInfoService;

    @GetMapping("/detail/{contentId}")
    public ResponseEntity findAttractionInfoDtoById(@NotNull @PathVariable int contentId) {
        return ResponseEntity.ok()
            .body(attractionInfoService.findAttractionInfoDtoById(contentId)
            );
    }

    @GetMapping("/list")
<<<<<<< HEAD
    public ResponseEntity findAttractionInfoNoOffset(@Valid @RequestBody AttractionInfoRequestDto attractionInfoRequestDto) {
=======
    public ResponseEntity findAttractionInfoNoOffset(@Valid @RequestParam Long lastId, @Valid @RequestParam Integer sidoCode, @Valid @RequestParam Integer contentTypeId, @Valid @RequestParam String title, @Valid @RequestParam Integer pageSize) {
        AttractionInfoRequestDto attractionInfoRequestDto = AttractionInfoRequestDto.builder()
            .lastId(lastId)
            .sidoCode(sidoCode)
            .contentTypeId(contentTypeId)
            .title(title)
            .pageSize(pageSize)
            .build();

>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
        return ResponseEntity.ok()
            .body(attractionInfoService.findAttractionInfoDtoNoOffset(attractionInfoRequestDto)
            );
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

    @ExceptionHandler(NoSuchAttractionInfoException.class)
    public ResponseEntity handleNoSuchAttractionInfoException(NoSuchAttractionInfoException e) {
        return ResponseEntity.badRequest()
            .body(
                Response.builder()
                    .cause(ATTRACTION.getMessage())
                    .message(e.getMessage())
                    .build()
            );
    }
}
