package com.ssafy.voyage.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gugun {
    @Id
    private int gugunCode;

    @Column(columnDefinition = "varchar(30) default NULL")
    private String gugunName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_code")
    private Sido sidoCode;
}
