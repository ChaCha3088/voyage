package com.ssafy.voyage.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attraction_detail")
public class AttractionDetail {
    @Id
    private int contentId;
	private String cat1;
	private String cat2;
	private String cat3;
	private String createdTime;
	private String modifiedTime;
	private String booktour;

}
