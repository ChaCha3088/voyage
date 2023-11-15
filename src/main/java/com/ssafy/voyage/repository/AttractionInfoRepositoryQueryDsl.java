package com.ssafy.voyage.repository;

import com.ssafy.voyage.entity.AttractionInfo;

import java.util.List;

public interface AttractionInfoRepositoryQueryDsl {
    List<AttractionInfo> findAttractionInfoNoOffset(long lastId, int sidoCode, int contentTypeId, String title, int pageSize);
}
