package com.example.weather.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.weather.model.Geodata;
import com.example.weather.model.Weather;
import com.example.weather.repository.GeodataRepository;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private GeodataRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        Geodata geodata = repository.findByName(name).get();
        String url = String.format("http://localhost:8082/?lat=%s&lon=%s", geodata.getLat(), geodata.getLon());
        return restTemplate.getForObject(url, Weather.class);
    }

    @GetMapping
    public List<Geodata> getAllLocations() {
        return (List<Geodata>) repository.findAll();
    }

    @GetMapping("/{name}")
    public Optional<Geodata> getLocation(@PathVariable String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Geodata save(@RequestBody Geodata geodata) {
        return repository.save(geodata);
    }

    @PutMapping("/{name}")
    public Geodata update(@PathVariable String name, @RequestBody Geodata geodata) {
        Geodata existingGeodata = repository.findByName(name).orElseThrow();
        existingGeodata.setLat(geodata.getLat());
        existingGeodata.setLon(geodata.getLon());
        existingGeodata.setName(geodata.getName());
        return repository.save(existingGeodata);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        Geodata geodata = repository.findByName(name).orElseThrow();
        repository.delete(geodata);
    }
}