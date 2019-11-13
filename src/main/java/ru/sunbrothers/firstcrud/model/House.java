package ru.sunbrothers.firstcrud.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numberOfFloors;
    @NonNull
    private String address;
    private String districtName;
    @NonNull
    private Integer buildDate;
    private Boolean ownParking;
}
