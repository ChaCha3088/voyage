package com.ssafy.voyage.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.voyage.entity.AttractionInfo;

public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Integer>{

    List<AttractionInfo> findAll(AttractionInfo attractionInfo);

	List<AttractionInfo> findByTitleContainingAndSidoCode(String title, int sidoCode) throws SQLException;
	
    List<AttractionInfo> findByContentId(int contentId) throws SQLException;

}