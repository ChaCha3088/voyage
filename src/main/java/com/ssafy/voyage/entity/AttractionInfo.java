package com.ssafy.voyage.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.voyage.dto.response.AttractionInfoDto;
import lombok.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "attraction_info")
@NoArgsConstructor
public class AttractionInfo {
    @Id
    private int contentId;

<<<<<<< HEAD
    @Column(columnDefinition = "default NULL")
	private int contentTypeId;
=======
    @ManyToOne(fetch = LAZY)
	private ContentType contentTypeId;
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21

    @Column(columnDefinition = "varchar(100) default NULL")
	private String title;

    @Column(columnDefinition = "varchar(100) default NULL")
	private String addr1;

    @Column(columnDefinition = "varchar(50) default NULL")
	private String addr2;

    @Column(columnDefinition = "varchar(50) default NULL")
	private String zipcode;

    @Column(columnDefinition = "varchar(50) default NULL")
	private String tel;

    @Column(columnDefinition = "varchar(200) default NULL")
	private String firstImage;

    @Column(columnDefinition = "varchar(200) default NULL")
	private String firstImage2;

    @Column(columnDefinition = "default NULL")
	private int readcount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sido_code")
    @JsonBackReference
    private Sido sidoCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gugun_code")
    @JsonBackReference
    private Gugun gugunCode;

    @Column(columnDefinition = "decimal(20, 17) default NULL")
	private double latitude;

    @Column(columnDefinition = "decimal(20, 17) default NULL")
	private double longitude;

    @Column(columnDefinition = "varchar(2) default NULL")
	private String mlevel;

    @OneToOne(mappedBy = "attractionInfo", fetch = LAZY)
    @PrimaryKeyJoinColumn
    private AttractionDetail attractionDetail;

    @OneToOne(mappedBy = "attractionInfo", fetch = LAZY)
    @PrimaryKeyJoinColumn
    private AttractionDescription attractionDescription;

    public AttractionInfoDto toAttractionInfoDtoForList() {
        return AttractionInfoDto.builder()
            .contentId(contentId)
            .attractionDetail(null)
            .attractionDescription(null)
<<<<<<< HEAD
            .contentTypeId(contentTypeId)
            .title(title)
            .addr1(null)
            .addr2(null)
            .zipcode(null)
            .tel(tel)
=======
            .contentTypeId(contentTypeId.getContentTypeId())
            .title(title)
            .addr1(addr1)
            .addr2(null)
            .zipcode(null)
            .tel(null)
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
            .firstImage(firstImage)
            .firstImage2(null)
            .readcount(null)
            .sidoCode(null)
            .gugunCode(null)
<<<<<<< HEAD
            .latitude(null)
            .longitude(null)
=======
            .latitude(latitude)
            .longitude(longitude)
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
            .mlevel(null)
        .build();
    }

    public AttractionInfoDto toAttractionInfoDtoWithAttractionDetailAndAttractionDescription() {
        return AttractionInfoDto.builder()
            .contentId(contentId)
            .attractionDetail(attractionDetail)
            .attractionDescription(attractionDescription)
<<<<<<< HEAD
            .contentTypeId(contentTypeId)
=======
            .contentTypeId(contentTypeId.getContentTypeId())
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
            .title(title)
            .addr1(addr1)
            .addr2(addr2)
            .zipcode(zipcode)
            .tel(tel)
            .firstImage(firstImage)
            .firstImage2(firstImage2)
            .readcount(readcount)
            .sidoCode(sidoCode.getSidoCode())
            .gugunCode(gugunCode.getGugunCode())
            .latitude(latitude)
            .longitude(longitude)
            .mlevel(mlevel)
        .build();
    }
}
