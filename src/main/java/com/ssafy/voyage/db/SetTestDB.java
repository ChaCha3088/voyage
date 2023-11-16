//package com.ssafy.voyage.db;
//
//import com.ssafy.voyage.entity.ContentType;
//import com.ssafy.voyage.repository.ContentTypeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Component
//public class SetTestDB {
//    @Autowired
//    private ContentTypeRepository contentTypeRepository;
//
//    @Transactional
//    @EventListener(ApplicationReadyEvent.class) // Application이 실행되고 나서 이 메소드를 실행한다.
//    public void setUp() {
//        ContentType 관광지 = ContentType.builder()
//            .contentTypeId(12)
//            .contentType("관광지")
//            .build();
//
//        ContentType 문화시설 = ContentType.builder()
//            .contentTypeId(14)
//            .contentType("문화시설")
//            .build();
//
//        ContentType 축제공연행사 = ContentType.builder()
//            .contentTypeId(15)
//            .contentType("축제공연행사")
//            .build();
//
//        ContentType 여행코스 = ContentType.builder()
//            .contentTypeId(25)
//            .contentType("여행코스")
//            .build();
//
//        ContentType 레포츠 = ContentType.builder()
//            .contentTypeId(28)
//            .contentType("레포츠")
//            .build();
//
//        ContentType 숙박 = ContentType.builder()
//            .contentTypeId(32)
//            .contentType("숙박")
//            .build();
//
//        ContentType 쇼핑 = ContentType.builder()
//            .contentTypeId(38)
//            .contentType("쇼핑")
//            .build();
//
//        ContentType 음식점 = ContentType.builder()
//            .contentTypeId(39)
//            .contentType("음식점")
//            .build();
//
//        contentTypeRepository.saveAll(List.of(관광지, 문화시설, 축제공연행사, 여행코스, 레포츠, 숙박, 쇼핑, 음식점));
//    }
//}
