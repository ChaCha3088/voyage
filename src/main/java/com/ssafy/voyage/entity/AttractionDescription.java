package com.ssafy.voyage.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

@Getter
@Entity(name = "attraction_description")
public class AttractionDescription {
    @Id
    private int contentId;
	private String homepage;
	private String overview;

}
