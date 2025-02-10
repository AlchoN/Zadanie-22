package com.example.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id @GeneratedValue
    int id;
    @NonNull private Double longitude;
    @NonNull private Double latitude;
    @NonNull private String name;
}