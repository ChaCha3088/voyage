package com.ssafy.voyage.repository;

import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
import com.ssafy.voyage.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

<<<<<<< HEAD
public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Long>, AttractionInfoRepositoryQueryDsl {
    List<AttractionInfo> findWithNoOffset(AttractionInfoRequestDto attractionInfoRequestDto);
}
=======
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.voyage.entity.AttractionInfo;

public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Integer>{

    Page<AttractionInfo> findAll(AttractionInfo attractionInfo);

    Page<AttractionInfo> findByTitleContainingAndSidoCode(String title, int sidoCode) throws SQLException;

    Page<AttractionInfo> findByContentId(int contentId) throws SQLException;

}
>>>>>>> 6be8287023fb2724aac2b54aa057de39240a7653
