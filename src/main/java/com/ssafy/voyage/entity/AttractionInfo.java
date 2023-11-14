package com.ssafy.voyage.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "attraction_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttractionInfo {
    @Id
    private int contentId;

    @OneToOne(mappedBy = "attractionInfo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AttractionDetail attractionDetail;

    @OneToOne(mappedBy = "attractionInfo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AttractionDescription attractionDescription;

    @Column(columnDefinition = "default NULL")
	private int contentTypeId;

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
	private Sido sidoCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gugun_code")
	private Gugun gugunCode;

    @Column(columnDefinition = "decimal(20, 17) default NULL")
	private double latitude;

    @Column(columnDefinition = "decimal(20, 17) default NULL")
	private double longitude;

    @Column(columnDefinition = "varchar(2) default NULL")
	private String mlevel;
}
