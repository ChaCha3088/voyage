package com.ssafy.voyage.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
<<<<<<< HEAD
=======
import com.ssafy.voyage.dto.response.SidoDto;
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
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
    @JsonManagedReference
    private List<Gugun> guguns = new ArrayList<>();

    @NotNull
    @OneToMany(mappedBy = "sidoCode")
    @JsonManagedReference
    private List<AttractionInfo> attractionInfos = new ArrayList<>();
<<<<<<< HEAD
=======

    // -- Dto -- //
    public SidoDto toDto() {
        return SidoDto.builder()
            .sidoCode(sidoCode)
            .sidoName(sidoName)
            .build();
    }
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
}
