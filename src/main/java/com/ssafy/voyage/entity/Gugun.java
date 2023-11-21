package com.ssafy.voyage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gugun {
    @Id
    private int gugunCode;

    @Column(columnDefinition = "varchar(30) default NULL")
    private String gugunName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sido_code")
    @JsonBackReference
    private Sido sidoCode;
}
