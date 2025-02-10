package com.example.Person.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    @Id @GeneratedValue
    private int id;

    @NonNull private String firstname;
    @NonNull private String surname;
    @NonNull private String lastname;
    @NonNull private LocalDate birthday;
    @NonNull private String location;
    @NonNull private Double lat;
    @NonNull private Double lon;

}