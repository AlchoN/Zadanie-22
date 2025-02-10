package com.example.Person.controller;

import com.example.Person.model.Person;
import com.example.Person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = repository.findById(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        Person savedPerson = repository.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable int id, @RequestBody Person person) {
        return repository.findById(id).map(existingPerson -> {
            existingPerson.setFirstname(person.getFirstname());
            existingPerson.setSurname(person.getSurname());
            existingPerson.setLastname(person.getLastname());
            existingPerson.setBirthday(person.getBirthday());
            existingPerson.setLocation(person.getLocation());
            Person updatedPerson = repository.save(existingPerson);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return repository.findById(id).map(existingPerson -> {
            repository.delete(existingPerson);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<Object> getWeather(@PathVariable int id) {
       Optional<Person> personOptional = repository.findById(id);
    Person person = personOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    String location = person.getLocation();
    String url = String.format("http://localhost:8082/weather?location=%s", location);
    Object weather = restTemplate.getForObject(url, Object.class);
    return new ResponseEntity<>(weather, HttpStatus.OK);
    }
}