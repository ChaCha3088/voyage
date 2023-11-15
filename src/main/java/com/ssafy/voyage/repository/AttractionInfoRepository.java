package com.ssafy.voyage.repository;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Long>, AttractionInfoRepositoryQueryDsl {
    List<AttractionInfo> findWithNoOffset(AttractionInfoRequestDto attractionInfoRequestDto);
}
