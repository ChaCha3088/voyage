package com.ssafy.voyage.controller.restcontroller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.voyage.entity.AttractionDescription;
import com.ssafy.voyage.entity.AttractionInfo;
import com.ssafy.voyage.service.AttractionService;

@RestController
@RequestMapping("/api/attraction")
public class AttractionInfoController {

    @Autowired
    AttractionService attractionService;
    
    @ResponseBody
    @GetMapping("/list")
    public List<AttractionInfo> attractionList(@ModelAttribute AttractionInfo attractionInfo) throws SQLException{
        return attractionService.findAll(attractionInfo);
    }

    @ResponseBody
    @GetMapping(value = "/search")
    public List<AttractionInfo> findByTitleContainingAndSidoCode(@ModelAttribute AttractionInfo attractionInfo) throws SQLException {
        return attractionService.findByTitleContainingAndSidoCode(attractionInfo.getTitle(), attractionInfo.getSidoCode());
    }

    @ResponseBody
    @GetMapping(value = "/search", params = "contentId")
    public List<AttractionInfo> findByContentId(@RequestParam("contentId") int contentId) throws SQLException {
        return attractionService.findByContentId(contentId);
    }

    @ResponseBody
    @GetMapping("/des/search")
    public List<AttractionDescription> findByContentIdForDescriptions(int contentId) throws SQLException {
        return attractionService.findByContentIdForDescriptions(contentId);
    }
}
