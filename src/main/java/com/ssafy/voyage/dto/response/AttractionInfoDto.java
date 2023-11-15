package com.ssafy.voyage.dto.response;

import com.ssafy.voyage.entity.AttractionDescription;
import com.ssafy.voyage.entity.AttractionDetail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AttractionInfoDto {
    private final int contentId;
    private final AttractionDetail attractionDetail;
    private final AttractionDescription attractionDescription;
    private final int contentTypeId;
    private final String title;
    private final String addr1;
    private final String addr2;
    private final String zipcode;
    private final String tel;
    private final String firstImage;
    private final String firstImage2;
    private final Integer readcount;
    private final Integer sidoCode;
    private final Integer gugunCode;
    private final Double latitude;
    private final Double longitude;
    private final String mlevel;

    @Builder
    protected AttractionInfoDto(int contentId, AttractionDetail attractionDetail, AttractionDescription attractionDescription, int contentTypeId, String title, String addr1, String addr2, String zipcode, String tel, String firstImage, String firstImage2, Integer readcount, Integer sidoCode, Integer gugunCode, Double latitude, Double longitude, String mlevel) {
        this.contentId = contentId;
        this.attractionDetail = attractionDetail;
        this.attractionDescription = attractionDescription;
        this.contentTypeId = contentTypeId;
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.zipcode = zipcode;
        this.tel = tel;
        this.firstImage = firstImage;
        this.firstImage2 = firstImage2;
        this.readcount = readcount;
        this.sidoCode = sidoCode;
        this.gugunCode = gugunCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mlevel = mlevel;
    }
}
