package com.ssafy.voyage.service;

import com.ssafy.voyage.dto.response.ContentTypeDto;
import com.ssafy.voyage.entity.ContentType;
import com.ssafy.voyage.repository.ContentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentTypeService {
    private final ContentTypeRepository contentTypeRepository;

    public List<ContentTypeDto> findAll() {
        return contentTypeRepository.findAll().stream()
            .map(ContentType::toContentTypeDto)
            .collect(Collectors.toList());
    }
}
