package tqs.consume_external_api.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tqs.consume_external_api.cache.Cache;
import tqs.consume_external_api.exception.CountryNotFoundException;
import tqs.consume_external_api.models.Country;
import tqs.consume_external_api.service.CountryClient;
import tqs.consume_external_api.service.CountryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CountryController {

    private CountryService service;

    CountryController() {
      this.service = new CountryService(new CountryClient());
    }

    @GetMapping("/countries")
    List<Country> allCountries() throws JsonMappingException, JsonProcessingException {
        return service.getAll();
    }

    @GetMapping("/countries/name={name}")
    Country oneCountryByName(@PathVariable String name) throws JsonMappingException, JsonProcessingException {
        return service.getCountry(name,false);
    }

    @GetMapping("/countries/ab={ab}")
    Country oneCountryByAb(@PathVariable String ab) throws JsonMappingException, JsonProcessingException {
        return service.getCountry(ab,true);
    }

    @GetMapping("/countries/status={status}")
    List<Country> allCountriesStatus(@PathVariable String status) throws JsonMappingException, JsonProcessingException {
        return service.getStatus(status);
    }

    @GetMapping("/countries/name={name}/status={status}")
    Country oneCountryByNameStatus(@PathVariable String name, @PathVariable String status) throws JsonMappingException, JsonProcessingException {
        return service.getCountryStatus(name, status, false);
    }

    @GetMapping("/countries/ab={ab}/status={status}")
    Country oneCountryByAbStatus(@PathVariable String ab, @PathVariable String status) throws JsonMappingException, JsonProcessingException {
        return service.getCountryStatus(ab, status, true);
    }

    @GetMapping("/cache")
    String cacheStats() {
        return service.getCacheStats();
    }

}
