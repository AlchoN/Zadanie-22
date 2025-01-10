package com.example.weather.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.weather.model.Geodata;

@Repository
public interface GeodataRepository extends CrudRepository<Geodata, Integer> {
    Optional<Geodata> findByName(String name);
}