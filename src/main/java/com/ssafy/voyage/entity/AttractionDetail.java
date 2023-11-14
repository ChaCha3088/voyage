package com.ssafy.voyage.entity;

import javax.persistence.*;

import lombok.*;

@Getter
@Entity(name = "attraction_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttractionDetail {
    @Id
    private int contentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "content_id")
    private AttractionInfo attractionInfo;

    @Column(columnDefinition = "varchar(3) default NULL")
	private String cat1;

    @Column(columnDefinition = "varchar(5) default NULL")
	private String cat2;

    @Column(columnDefinition = "varchar(9) default NULL")
	private String cat3;

    @Column(columnDefinition = "varchar(14) default NULL")
	private String createdTime;

    @Column(columnDefinition = "varchar(14) default NULL")
	private String modifiedTime;

    @Column(columnDefinition = "varchar(5) default NULL")
	private String booktour;
}
