package com.ssafy.voyage.service;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.dto.response.AttractionInfoDto;
import com.ssafy.voyage.entity.AttractionInfo;
import com.ssafy.voyage.exception.NoSuchAttractionInfoException;
import com.ssafy.voyage.repository.AttractionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.voyage.message.message.AttractionMessages.ATTRACTION;
import static com.ssafy.voyage.message.message.Messages.NOT_EXISTS;
import static com.ssafy.voyage.message.message.Messages.SUCH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionInfoService {
    private final AttractionInfoRepository attractionInfoRepository;

    public AttractionInfoDto findAttractionInfoDtoById(int contentId) throws NoSuchAttractionInfoException {
        return attractionInfoRepository.findByContentIdWithDetailAndDescription(contentId)
            .orElseThrow(() -> new NoSuchAttractionInfoException(new StringBuffer().append(SUCH.getMessage()).append(ATTRACTION.getMessage()).append(NOT_EXISTS.getMessage()).toString()))
            .toAttractionInfoDtoWithAttractionDetailAndAttractionDescription();
    }

    public List<AttractionInfoDto> findAttractionInfoDtoNoOffset(AttractionInfoRequestDto attractionInfoRequestDto) {
        List<AttractionInfo> attractionInfos = attractionInfoRepository.findWithNoOffset(attractionInfoRequestDto);

        return attractionInfos.stream()
            .map(AttractionInfo::toAttractionInfoDtoForList)
            .collect(Collectors.toList());
    }
}
