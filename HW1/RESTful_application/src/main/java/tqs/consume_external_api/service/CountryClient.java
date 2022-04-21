package tqs.consume_external_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CountryClient {

    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(CountryClient.class);

    public CountryClient() {
        restTemplate = new RestTemplate();
    }

    public JsonNode[] fetchOneCountry(String countryURL, boolean ab)
            throws JsonMappingException, JsonProcessingException {

        String id = ab ? "ab" : "country";

        String[] statusURL = { "confirmed", "deaths" };
        String url_confirmed = "https://covid-api.mmediagroup.fr/v1/history?" + id + "=" + countryURL + "&status="
                + statusURL[0];
        String url_deaths = "https://covid-api.mmediagroup.fr/v1/history?" + id + "=" + countryURL + "&status="
                + statusURL[1];

        ResponseEntity<String> response_confirmed = restTemplate.getForEntity(url_confirmed, String.class);
        ResponseEntity<String> response_deaths = restTemplate.getForEntity(url_deaths, String.class);

        if ((response_confirmed.getStatusCode() != HttpStatus.OK)
                || (response_deaths.getStatusCode() != HttpStatus.OK)) {
            log.info("CLIENT: fetch of " + countryURL + " unsuccessful.");
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode country_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode country_deaths = mapper.readTree(response_deaths.getBody());

        log.info("CLIENT: fetch of " + countryURL + " successful.");
        JsonNode[] ret = {country_confirmed, country_deaths};
        return ret;

    }

    public ArrayList<JsonNode[]> fetchMultipleCountries() throws JsonMappingException, JsonProcessingException {

        ArrayList<JsonNode[]> ret = new ArrayList<>();

        String[] statusURL = { "confirmed", "deaths" };
        String url_confirmed = "https://covid-api.mmediagroup.fr/v1/history?status=" + statusURL[0];
        String url_deaths = "https://covid-api.mmediagroup.fr/v1/history?status=" + statusURL[1];

        ResponseEntity<String> response_confirmed = restTemplate.getForEntity(url_confirmed, String.class);
        ResponseEntity<String> response_deaths = restTemplate.getForEntity(url_deaths, String.class);

        if ((response_confirmed.getStatusCode() != HttpStatus.OK)
                || (response_deaths.getStatusCode() != HttpStatus.OK)) {
            log.info("CLIENT: fetch of all countries unsuccessful.");
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode countries_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode countries_deaths = mapper.readTree(response_deaths.getBody());

        Iterator<String> countries_iterator = countries_confirmed.fieldNames();
        countries_iterator.forEachRemaining((country_name) -> {
            JsonNode country_confirmed = countries_confirmed.get(country_name);
            JsonNode country_deaths = countries_deaths.get(country_name);
            JsonNode[] country_data = {country_confirmed, country_deaths};
            ret.add(country_data);
        });

        log.info("CLIENT: fetch of all countries successful.");
        return ret;

    }

}
