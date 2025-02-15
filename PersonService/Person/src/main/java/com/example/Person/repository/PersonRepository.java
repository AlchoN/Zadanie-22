package com.example.Person.repository;

import com.example.Person.model.Person;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Optional<Person> findById(int id);
}