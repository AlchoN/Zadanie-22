package com.example.weather.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.weather.model.Location;

@Repository
public interface GeodataRepository extends CrudRepository<Location, Integer> {
    Optional<Location> findByName(String name);
    Optional<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
