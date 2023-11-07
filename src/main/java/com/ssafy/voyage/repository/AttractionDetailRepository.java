package com.ssafy.voyage.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.voyage.entity.AttractionDescription;

@Repository
public interface AttractionDetailRepository extends JpaRepository<AttractionDescription, Integer>{
    List<AttractionDescription> findByContentId(int contentId) throws SQLException;
}
