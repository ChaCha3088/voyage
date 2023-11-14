package com.ssafy.voyage.service;

import java.sql.SQLException;
import java.util.List;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.voyage.entity.AttractionDescription;
import com.ssafy.voyage.entity.AttractionInfo;
import com.ssafy.voyage.repository.AttractionDetailRepository;
import com.ssafy.voyage.repository.AttractionInfoRepository;
import org.w3c.dom.Attr;

@Log
@Service
public class AttractionService {

    @Autowired
    private AttractionInfoRepository attractionRepository;

    @Autowired
    private AttractionDetailRepository attractionDetailRepository;

    public Page<AttractionInfo> findAll(AttractionInfo attractionInfo, int page) throws SQLException {
        Pageable pageable = PageRequest.of(page, 10);
        Page<AttractionInfo> res = attractionRepository.findAll(pageable);
        //List<AttractionInfo>  res = attractionRepository.findAll(attractionInfo, page);
        return  res;
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