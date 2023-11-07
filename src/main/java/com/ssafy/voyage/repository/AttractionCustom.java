package com.ssafy.voyage.repository;

import java.util.List;

import com.ssafy.voyage.entity.AttractionInfo;

public interface AttractionCustom {
    List<AttractionInfo> findAll(AttractionInfo attractionInfo);
}
