package com.example.weather.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.weather.model.Location;
import com.example.weather.model.Weather;
import com.example.weather.repository.GeodataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/location")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private GeodataRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam Double latitude, Double longitude ) {
        Location geodata = repository.findByLatitudeAndLongitude(latitude, longitude).get();
        String url = String.format("http://localhost:8083/weather?latitude=%f&longitude=%f", geodata.getLatitude(), geodata.getLongitude());
        return restTemplate.getForObject(url, Weather.class);
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return (List<Location>) repository.findAll();
    }

    @GetMapping("/{name}")
    public Optional<Location> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Location save(@RequestBody Location geodata) {
        logger.info("Received POST request with data: {}", geodata);
        return repository.save(geodata);
    }

    @PutMapping
    public Location update(@RequestParam String name, @RequestBody Location geodata) {
        Location existingGeodata = repository.findByName(name).orElseThrow();
        existingGeodata.setLatitude(geodata.getLatitude());
        existingGeodata.setLongitude(geodata.getLongitude());
        existingGeodata.setName(geodata.getName());
        return repository.save(existingGeodata);
    }

    @DeleteMapping
    public void delete(@RequestParam String name) {
        Location geodata = repository.findByName(name).orElseThrow();
        repository.delete(geodata);
    }
}