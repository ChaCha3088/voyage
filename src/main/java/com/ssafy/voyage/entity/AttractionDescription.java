package com.ssafy.voyage.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Getter
@Entity(name = "attraction_description")
public class AttractionDescription {
    @Id
    private int contentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "content_id")
    @JsonBackReference
    private AttractionInfo attractionInfo;

    @Column(columnDefinition = "varchar(100) default NULL")
	private String homepage;

    @Column(columnDefinition = "varchar(10000) default NULL")
	private String overview;

    @Column(columnDefinition = "varchar(45) default NULL")
    private String telname;
}
