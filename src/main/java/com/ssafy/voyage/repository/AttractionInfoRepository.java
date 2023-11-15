package com.ssafy.voyage.repository;

import com.ssafy.voyage.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Long>, AttractionInfoRepositoryQueryDsl {
    List<AttractionInfo> findAttractionInfoNoOffset(long lastId, int sidoCode, int contentTypeId, String title, int pageSize);
}
