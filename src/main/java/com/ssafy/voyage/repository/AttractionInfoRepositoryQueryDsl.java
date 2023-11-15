package com.ssafy.voyage.repository;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.entity.AttractionInfo;

import java.util.List;
import java.util.Optional;

public interface AttractionInfoRepositoryQueryDsl {
    Optional<AttractionInfo> findByContentIdWithDetailAndDescription(int contentId);
    List<AttractionInfo> findWithNoOffset(AttractionInfoRequestDto attractionInfoRequestDto);
}
