<<<<<<< HEAD
package com.ssafy.voyage.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zipcode;

    @Builder
    protected Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
=======
package com.ssafy.voyage.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zipcode;

    @Builder
    protected Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
>>>>>>> f516500ccb7cee30e4cc4c7da4a3441a682a4b21
