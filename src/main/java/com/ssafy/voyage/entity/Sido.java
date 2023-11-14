package com.ssafy.voyage.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sido {
    @Id
    private int sidoCode;
    @Column(columnDefinition = "varchar(30) default NULL")
    private String sidoName;

    @NotNull
    @OneToMany(mappedBy = "sidoCode")
    private List<Gugun> guguns = new ArrayList<>();

    @NotNull
    @OneToMany(mappedBy = "sidoCode")
    private List<AttractionInfo> attractionInfos = new ArrayList<>();
}
