package com.ssafy.voyage.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.voyage.entity.AttractionDescription;
import com.ssafy.voyage.entity.AttractionInfo;
import com.ssafy.voyage.repository.AttractionDetailRepository;
import com.ssafy.voyage.repository.AttractionInfoRepository;

@Service
public class AttractionService {

    @Autowired
    private AttractionInfoRepository attractionRepository;

    @Autowired
    private AttractionDetailRepository attractionDetailRepository;

    public List<AttractionInfo> findAll(AttractionInfo attractionInfo) throws SQLException {
        return attractionRepository.findAll(attractionInfo);
    }


	public List<AttractionInfo> findByTitleContainingAndSidoCode(String title, int sidoCode) throws SQLException {
        return attractionRepository.findByTitleContainingAndSidoCode(title, sidoCode);
    }

    public List<AttractionInfo> findByContentId(int contentId) throws SQLException {
        return attractionRepository.findByContentId(contentId);
    }

	public List<AttractionDescription> findByContentIdForDescriptions(int contentId) throws SQLException {
        return attractionDetailRepository.findByContentId(contentId);
    }

}