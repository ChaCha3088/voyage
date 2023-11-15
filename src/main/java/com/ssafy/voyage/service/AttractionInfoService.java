package com.ssafy.voyage.service;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.dto.response.AttractionInfoDto;
import com.ssafy.voyage.entity.AttractionInfo;
import com.ssafy.voyage.repository.AttractionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionInfoService {
    private final AttractionInfoRepository attractionInfoRepository;

    public List<AttractionInfoDto> findAttractionInfoDtoNoOffset(AttractionInfoRequestDto attractionInfoRequestDto) {
        List<AttractionInfo> attractionInfos = attractionInfoRepository.findAttractionInfoNoOffset(
            attractionInfoRequestDto.getLastId(),
            attractionInfoRequestDto.getSidoCode(),
            attractionInfoRequestDto.getContentTypeId(),
            attractionInfoRequestDto.getTitle(),
            attractionInfoRequestDto.getPageSize()
        );
        return attractionInfos.stream()
            .map(AttractionInfo::toAttractionInfoDto)
            .collect(Collectors.toList());
    }
}
