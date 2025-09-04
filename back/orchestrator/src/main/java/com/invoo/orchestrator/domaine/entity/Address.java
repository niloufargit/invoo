package com.invoo.orchestrator.domaine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    private Long id;
    private String streetNumber;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    private Long userId;
}
