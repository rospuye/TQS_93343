package tqs.consume_external_api;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tqs.consume_external_api.cache.Cache;
import tqs.consume_external_api.models.All;
import tqs.consume_external_api.models.Country;
import tqs.consume_external_api.models.Territory;
import tqs.consume_external_api.service.CountryClient;
import tqs.consume_external_api.service.CountryService;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class B_ServiceLevelTests {

    @Mock
    CountryClient mockClient = mock(CountryClient.class);

    private CountryService service;
    private Cache cache;

    @BeforeEach
    public void setUp() {
        this.cache = Cache.getInstance();
        this.service = new CountryService(cache, mockClient);
    }

    /*
     * Test if the service can correctly process a country by its name identifier.
     */
    @Test
    public void testGetCountryName() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        String response_confirmed_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 3757590, \"2022-04-19\": 3745569, \"2022-04-18\": 3719485}}}";
        String response_deaths_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 22106, \"2022-04-19\": 22088, \"2022-04-18\": 21993}}}";

        ResponseEntity<String> response_confirmed = new ResponseEntity<>(response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> response_deaths = new ResponseEntity<>(response_deaths_str, HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode country_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode country_deaths = mapper.readTree(response_deaths.getBody());
        JsonNode[] mock_ret = { country_confirmed, country_deaths };

        when(mockClient.fetchOneCountry("Portugal", false)).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_portugal = new Country();
        All expected_portugal_all = new All();
        expected_portugal_all.setCountry("Portugal");
        expected_portugal_all.setAbbreviation("PT");
        LinkedHashMap<String, Integer> expected_portugal_confirmed_dates = new LinkedHashMap<>();
        expected_portugal_confirmed_dates.put("2022-04-20", 3757590);
        expected_portugal_confirmed_dates.put("2022-04-19", 3745569);
        expected_portugal_confirmed_dates.put("2022-04-18", 3719485);
        LinkedHashMap<String, Integer> expected_portugal_deaths_dates = new LinkedHashMap<>();
        expected_portugal_deaths_dates.put("2022-04-20", 22106);
        expected_portugal_deaths_dates.put("2022-04-19", 22088);
        expected_portugal_deaths_dates.put("2022-04-18", 21993);
        expected_portugal_all.setConfirmed_dates(expected_portugal_confirmed_dates);
        expected_portugal_all.setDeaths_dates(expected_portugal_deaths_dates);
        expected_portugal.setAll(expected_portugal_all);

        // testing the service

        Country actual_portugal = service.getCountry("Portugal", false);

        assertThat(actual_portugal.toString(), is(equalTo(expected_portugal.toString())));
        Mockito.verify(mockClient).fetchOneCountry("Portugal", false);

    }

    /*
     * Test if the service can correctly process a country by its abbreviation
     * identifier.
     */
    @Test
    public void testGetCountryAb() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        String response_confirmed_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 3757590, \"2022-04-19\": 3745569, \"2022-04-18\": 3719485}}}";
        String response_deaths_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 22106, \"2022-04-19\": 22088, \"2022-04-18\": 21993}}}";

        ResponseEntity<String> response_confirmed = new ResponseEntity<>(response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> response_deaths = new ResponseEntity<>(response_deaths_str, HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode country_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode country_deaths = mapper.readTree(response_deaths.getBody());
        JsonNode[] mock_ret = { country_confirmed, country_deaths };

        when(mockClient.fetchOneCountry("PT", true)).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_portugal = new Country();
        All expected_portugal_all = new All();
        expected_portugal_all.setCountry("Portugal");
        expected_portugal_all.setAbbreviation("PT");
        LinkedHashMap<String, Integer> expected_portugal_confirmed_dates = new LinkedHashMap<>();
        expected_portugal_confirmed_dates.put("2022-04-20", 3757590);
        expected_portugal_confirmed_dates.put("2022-04-19", 3745569);
        expected_portugal_confirmed_dates.put("2022-04-18", 3719485);
        LinkedHashMap<String, Integer> expected_portugal_deaths_dates = new LinkedHashMap<>();
        expected_portugal_deaths_dates.put("2022-04-20", 22106);
        expected_portugal_deaths_dates.put("2022-04-19", 22088);
        expected_portugal_deaths_dates.put("2022-04-18", 21993);
        expected_portugal_all.setConfirmed_dates(expected_portugal_confirmed_dates);
        expected_portugal_all.setDeaths_dates(expected_portugal_deaths_dates);
        expected_portugal.setAll(expected_portugal_all);

        // testing the service

        Country actual_portugal = service.getCountry("PT", true);

        assertThat(actual_portugal.toString(), is(equalTo(expected_portugal.toString())));
        Mockito.verify(mockClient).fetchOneCountry("PT", true);

    }

    /*
     * Test if the service can correctly process a country which has external
     * territories in addition to its main land.
     */
    @Test
    public void testGetCountryWithTerritories() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        String response_confirmed_str = "{\"All\": {\"country\": \"France\", \"population\": 64979548, \"sq_km_area\": 551500, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 375, \"continent\": \"Europe\", \"abbreviation\": \"FR\", \"location\": \"Western Europe\", \"iso\": 250, \"capital_city\": \"Paris\", \"dates\": {\"2022-04-20\": 27239081, \"2022-04-19\": 27083370, \"2022-04-18\": 27062073}}, \"French Guiana\": {\"dates\": {\"2022-04-20\": 80035, \"2022-04-19\": 80035, \"2022-04-18\": 79529}}, \"French Polynesia\": {\"dates\": {\"2022-04-20\": 72621, \"2022-04-19\": 72621, \"2022-04-18\": 72596}}, \"Guadeloupe\": {\"dates\": {\"2022-04-20\": 145228, \"2022-04-19\": 145228, \"2022-04-18\": 142573}}}";
        String response_deaths_str = "{\"All\": {\"country\": \"France\", \"population\": 64979548, \"sq_km_area\": 551500, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 375, \"continent\": \"Europe\", \"abbreviation\": \"FR\", \"location\": \"Western Europe\", \"iso\": 250, \"capital_city\": \"Paris\", \"dates\": {\"2022-04-20\": 141443, \"2022-04-19\": 141216, \"2022-04-18\": 141035}}, \"French Guiana\": {\"dates\": {\"2022-04-20\": 394, \"2022-04-19\": 394, \"2022-04-18\": 394}}, \"French Polynesia\": {\"dates\": {\"2022-04-20\": 648, \"2022-04-19\": 648, \"2022-04-18\": 648}}, \"Guadeloupe\": {\"dates\": {\"2022-04-20\": 941, \"2022-04-19\": 941, \"2022-04-18\": 940}}}";

        ResponseEntity<String> response_confirmed = new ResponseEntity<>(response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> response_deaths = new ResponseEntity<>(response_deaths_str, HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode country_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode country_deaths = mapper.readTree(response_deaths.getBody());
        JsonNode[] mock_ret = { country_confirmed, country_deaths };

        when(mockClient.fetchOneCountry("France", false)).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_france = new Country();

        All expected_france_all = new All();
        expected_france_all.setCountry("France");
        expected_france_all.setAbbreviation("FR");
        LinkedHashMap<String, Integer> expected_france_confirmed_dates = new LinkedHashMap<>();
        expected_france_confirmed_dates.put("2022-04-20", 27239081);
        expected_france_confirmed_dates.put("2022-04-19", 27083370);
        expected_france_confirmed_dates.put("2022-04-18", 27062073);
        LinkedHashMap<String, Integer> expected_france_deaths_dates = new LinkedHashMap<>();
        expected_france_deaths_dates.put("2022-04-20", 141443);
        expected_france_deaths_dates.put("2022-04-19", 141216);
        expected_france_deaths_dates.put("2022-04-18", 141035);
        expected_france_all.setConfirmed_dates(expected_france_confirmed_dates);
        expected_france_all.setDeaths_dates(expected_france_deaths_dates);
        expected_france.setAll(expected_france_all);

        ArrayList<Territory> expected_france_territories = new ArrayList<>();

        Territory expected_french_guiana = new Territory();
        expected_french_guiana.setTerritory("French Guiana");
        LinkedHashMap<String, Integer> expected_french_guiana_confirmed_dates = new LinkedHashMap<>();
        expected_french_guiana_confirmed_dates.put("2022-04-20", 80035);
        expected_french_guiana_confirmed_dates.put("2022-04-19", 80035);
        expected_french_guiana_confirmed_dates.put("2022-04-18", 79529);
        expected_french_guiana.setConfirmed_dates(expected_french_guiana_confirmed_dates);
        LinkedHashMap<String, Integer> expected_french_guiana_deaths_dates = new LinkedHashMap<>();
        expected_french_guiana_deaths_dates.put("2022-04-20", 394);
        expected_french_guiana_deaths_dates.put("2022-04-19", 394);
        expected_french_guiana_deaths_dates.put("2022-04-18", 394);
        expected_french_guiana.setDeaths_dates(expected_french_guiana_deaths_dates);
        expected_france_territories.add(expected_french_guiana);

        Territory expected_french_polynesia = new Territory();
        expected_french_polynesia.setTerritory("French Polynesia");
        LinkedHashMap<String, Integer> expected_french_polynesia_confirmed_dates = new LinkedHashMap<>();
        expected_french_polynesia_confirmed_dates.put("2022-04-20", 72621);
        expected_french_polynesia_confirmed_dates.put("2022-04-19", 72621);
        expected_french_polynesia_confirmed_dates.put("2022-04-18", 72596);
        expected_french_polynesia.setConfirmed_dates(expected_french_polynesia_confirmed_dates);
        LinkedHashMap<String, Integer> expected_french_polynesia_deaths_dates = new LinkedHashMap<>();
        expected_french_polynesia_deaths_dates.put("2022-04-20", 648);
        expected_french_polynesia_deaths_dates.put("2022-04-19", 648);
        expected_french_polynesia_deaths_dates.put("2022-04-18", 648);
        expected_french_polynesia.setDeaths_dates(expected_french_polynesia_deaths_dates);
        expected_france_territories.add(expected_french_polynesia);

        Territory expected_guadeloupe = new Territory();
        expected_guadeloupe.setTerritory("Guadeloupe");
        LinkedHashMap<String, Integer> expected_guadeloupe_confirmed_dates = new LinkedHashMap<>();
        expected_guadeloupe_confirmed_dates.put("2022-04-20", 145228);
        expected_guadeloupe_confirmed_dates.put("2022-04-19", 145228);
        expected_guadeloupe_confirmed_dates.put("2022-04-18", 142573);
        expected_guadeloupe.setConfirmed_dates(expected_guadeloupe_confirmed_dates);
        LinkedHashMap<String, Integer> expected_guadeloupe_deaths_dates = new LinkedHashMap<>();
        expected_guadeloupe_deaths_dates.put("2022-04-20", 941);
        expected_guadeloupe_deaths_dates.put("2022-04-19", 941);
        expected_guadeloupe_deaths_dates.put("2022-04-18", 940);
        expected_guadeloupe.setDeaths_dates(expected_guadeloupe_deaths_dates);
        expected_france_territories.add(expected_guadeloupe);

        expected_france.setTerritories(expected_france_territories);

        // testing the service

        Country actual_france = service.getCountry("France", false);

        assertThat(actual_france.toString(), is(equalTo(expected_france.toString())));
        Mockito.verify(mockClient).fetchOneCountry("France", false);

    }

    /* Test if the service can correctly process a country's status. */
    @Test
    public void testGetCountryStatus() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        String response_confirmed_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 3757590, \"2022-04-19\": 3745569, \"2022-04-18\": 3719485}}}";
        String response_deaths_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 22106, \"2022-04-19\": 22088, \"2022-04-18\": 21993}}}";

        ResponseEntity<String> response_confirmed = new ResponseEntity<>(response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> response_deaths = new ResponseEntity<>(response_deaths_str, HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode country_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode country_deaths = mapper.readTree(response_deaths.getBody());
        JsonNode[] mock_ret = { country_confirmed, country_deaths };

        when(mockClient.fetchOneCountry("Portugal", false)).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_portugal = new Country();
        All expected_portugal_all = new All();
        expected_portugal_all.setCountry("Portugal");
        expected_portugal_all.setAbbreviation("PT");
        LinkedHashMap<String, Integer> expected_portugal_confirmed_dates = new LinkedHashMap<>();
        expected_portugal_confirmed_dates.put("2022-04-20", 3757590);
        expected_portugal_confirmed_dates.put("2022-04-19", 3745569);
        expected_portugal_confirmed_dates.put("2022-04-18", 3719485);
        LinkedHashMap<String, Integer> expected_portugal_deaths_dates = null;
        expected_portugal_all.setConfirmed_dates(expected_portugal_confirmed_dates);
        expected_portugal_all.setDeaths_dates(expected_portugal_deaths_dates);
        expected_portugal.setAll(expected_portugal_all);

        // testing the service

        Country actual_portugal = service.getCountryStatus("Portugal", "confirmed", false);

        assertThat(actual_portugal.toString(), is(equalTo(expected_portugal.toString())));
        Mockito.verify(mockClient).fetchOneCountry("Portugal", false);

    }

    /* Test if the service can correctly process a list of countries. */
    @Test
    public void testGetAllCountries() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        ObjectMapper mapper = new ObjectMapper();

        String pt_response_confirmed_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 3757590, \"2022-04-19\": 3745569, \"2022-04-18\": 3719485}}}";
        String pt_response_deaths_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 22106, \"2022-04-19\": 22088, \"2022-04-18\": 21993}}}";

        ResponseEntity<String> pt_response_confirmed = new ResponseEntity<>(pt_response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> pt_response_deaths = new ResponseEntity<>(pt_response_deaths_str, HttpStatus.OK);
        JsonNode pt_country_confirmed = mapper.readTree(pt_response_confirmed.getBody());
        JsonNode pt_country_deaths = mapper.readTree(pt_response_deaths.getBody());
        JsonNode[] pt_mock_ret = { pt_country_confirmed, pt_country_deaths };

        String fr_response_confirmed_str = "{\"All\": {\"country\": \"France\", \"population\": 64979548, \"sq_km_area\": 551500, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 375, \"continent\": \"Europe\", \"abbreviation\": \"FR\", \"location\": \"Western Europe\", \"iso\": 250, \"capital_city\": \"Paris\", \"dates\": {\"2022-04-20\": 27239081, \"2022-04-19\": 27083370, \"2022-04-18\": 27062073}}, \"French Guiana\": {\"dates\": {\"2022-04-20\": 80035, \"2022-04-19\": 80035, \"2022-04-18\": 79529}}, \"French Polynesia\": {\"dates\": {\"2022-04-20\": 72621, \"2022-04-19\": 72621, \"2022-04-18\": 72596}}, \"Guadeloupe\": {\"dates\": {\"2022-04-20\": 145228, \"2022-04-19\": 145228, \"2022-04-18\": 142573}}}";
        String fr_response_deaths_str = "{\"All\": {\"country\": \"France\", \"population\": 64979548, \"sq_km_area\": 551500, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 375, \"continent\": \"Europe\", \"abbreviation\": \"FR\", \"location\": \"Western Europe\", \"iso\": 250, \"capital_city\": \"Paris\", \"dates\": {\"2022-04-20\": 141443, \"2022-04-19\": 141216, \"2022-04-18\": 141035}}, \"French Guiana\": {\"dates\": {\"2022-04-20\": 394, \"2022-04-19\": 394, \"2022-04-18\": 394}}, \"French Polynesia\": {\"dates\": {\"2022-04-20\": 648, \"2022-04-19\": 648, \"2022-04-18\": 648}}, \"Guadeloupe\": {\"dates\": {\"2022-04-20\": 941, \"2022-04-19\": 941, \"2022-04-18\": 940}}}";

        ResponseEntity<String> fr_response_confirmed = new ResponseEntity<>(fr_response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> fr_response_deaths = new ResponseEntity<>(fr_response_deaths_str, HttpStatus.OK);
        JsonNode fr_country_confirmed = mapper.readTree(fr_response_confirmed.getBody());
        JsonNode fr_country_deaths = mapper.readTree(fr_response_deaths.getBody());
        JsonNode[] fr_mock_ret = { fr_country_confirmed, fr_country_deaths };

        String es_response_confirmed_str = "{\"All\": {\"country\": \"Spain\", \"population\": 46354321, \"sq_km_area\": 505992, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 660, \"continent\": \"Europe\", \"abbreviation\": \"ES\", \"location\": \"Southern Europe\", \"iso\": 724, \"capital_city\": \"Madrid\", \"dates\": {\"2022-04-20\": 11736893, \"2022-04-19\": 11736893, \"2022-04-18\": 11662214}}}";
        String es_response_deaths_str = "{\"All\": {\"country\": \"Spain\", \"population\": 46354321, \"sq_km_area\": 505992, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 660, \"continent\": \"Europe\", \"abbreviation\": \"ES\", \"location\": \"Southern Europe\", \"iso\": 724, \"capital_city\": \"Madrid\", \"dates\": {\"2022-04-20\": 103721, \"2022-04-19\": 103104, \"2022-04-18\": 103104}}}";

        ResponseEntity<String> es_response_confirmed = new ResponseEntity<>(es_response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> es_response_deaths = new ResponseEntity<>(es_response_deaths_str, HttpStatus.OK);
        JsonNode es_country_confirmed = mapper.readTree(es_response_confirmed.getBody());
        JsonNode es_country_deaths = mapper.readTree(es_response_deaths.getBody());
        JsonNode[] es_mock_ret = { es_country_confirmed, es_country_deaths };

        ArrayList<JsonNode[]> mock_ret = new ArrayList<>();
        mock_ret.add(pt_mock_ret);
        mock_ret.add(fr_mock_ret);
        mock_ret.add(es_mock_ret);

        when(mockClient.fetchMultipleCountries()).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_portugal = new Country();
        All expected_portugal_all = new All();
        expected_portugal_all.setCountry("Portugal");
        expected_portugal_all.setAbbreviation("PT");
        LinkedHashMap<String, Integer> expected_portugal_confirmed_dates = new LinkedHashMap<>();
        expected_portugal_confirmed_dates.put("2022-04-20", 3757590);
        expected_portugal_confirmed_dates.put("2022-04-19", 3745569);
        expected_portugal_confirmed_dates.put("2022-04-18", 3719485);
        LinkedHashMap<String, Integer> expected_portugal_deaths_dates = new LinkedHashMap<>();
        expected_portugal_deaths_dates.put("2022-04-20", 22106);
        expected_portugal_deaths_dates.put("2022-04-19", 22088);
        expected_portugal_deaths_dates.put("2022-04-18", 21993);
        expected_portugal_all.setConfirmed_dates(expected_portugal_confirmed_dates);
        expected_portugal_all.setDeaths_dates(expected_portugal_deaths_dates);
        expected_portugal.setAll(expected_portugal_all);

        Country expected_france = new Country();

        All expected_france_all = new All();
        expected_france_all.setCountry("France");
        expected_france_all.setAbbreviation("FR");
        LinkedHashMap<String, Integer> expected_france_confirmed_dates = new LinkedHashMap<>();
        expected_france_confirmed_dates.put("2022-04-20", 27239081);
        expected_france_confirmed_dates.put("2022-04-19", 27083370);
        expected_france_confirmed_dates.put("2022-04-18", 27062073);
        LinkedHashMap<String, Integer> expected_france_deaths_dates = new LinkedHashMap<>();
        expected_france_deaths_dates.put("2022-04-20", 141443);
        expected_france_deaths_dates.put("2022-04-19", 141216);
        expected_france_deaths_dates.put("2022-04-18", 141035);
        expected_france_all.setConfirmed_dates(expected_france_confirmed_dates);
        expected_france_all.setDeaths_dates(expected_france_deaths_dates);
        expected_france.setAll(expected_france_all);

        ArrayList<Territory> expected_france_territories = new ArrayList<>();

        Territory expected_french_guiana = new Territory();
        expected_french_guiana.setTerritory("French Guiana");
        LinkedHashMap<String, Integer> expected_french_guiana_confirmed_dates = new LinkedHashMap<>();
        expected_french_guiana_confirmed_dates.put("2022-04-20", 80035);
        expected_french_guiana_confirmed_dates.put("2022-04-19", 80035);
        expected_french_guiana_confirmed_dates.put("2022-04-18", 79529);
        expected_french_guiana.setConfirmed_dates(expected_french_guiana_confirmed_dates);
        LinkedHashMap<String, Integer> expected_french_guiana_deaths_dates = new LinkedHashMap<>();
        expected_french_guiana_deaths_dates.put("2022-04-20", 394);
        expected_french_guiana_deaths_dates.put("2022-04-19", 394);
        expected_french_guiana_deaths_dates.put("2022-04-18", 394);
        expected_french_guiana.setDeaths_dates(expected_french_guiana_deaths_dates);
        expected_france_territories.add(expected_french_guiana);

        Territory expected_french_polynesia = new Territory();
        expected_french_polynesia.setTerritory("French Polynesia");
        LinkedHashMap<String, Integer> expected_french_polynesia_confirmed_dates = new LinkedHashMap<>();
        expected_french_polynesia_confirmed_dates.put("2022-04-20", 72621);
        expected_french_polynesia_confirmed_dates.put("2022-04-19", 72621);
        expected_french_polynesia_confirmed_dates.put("2022-04-18", 72596);
        expected_french_polynesia.setConfirmed_dates(expected_french_polynesia_confirmed_dates);
        LinkedHashMap<String, Integer> expected_french_polynesia_deaths_dates = new LinkedHashMap<>();
        expected_french_polynesia_deaths_dates.put("2022-04-20", 648);
        expected_french_polynesia_deaths_dates.put("2022-04-19", 648);
        expected_french_polynesia_deaths_dates.put("2022-04-18", 648);
        expected_french_polynesia.setDeaths_dates(expected_french_polynesia_deaths_dates);
        expected_france_territories.add(expected_french_polynesia);

        Territory expected_guadeloupe = new Territory();
        expected_guadeloupe.setTerritory("Guadeloupe");
        LinkedHashMap<String, Integer> expected_guadeloupe_confirmed_dates = new LinkedHashMap<>();
        expected_guadeloupe_confirmed_dates.put("2022-04-20", 145228);
        expected_guadeloupe_confirmed_dates.put("2022-04-19", 145228);
        expected_guadeloupe_confirmed_dates.put("2022-04-18", 142573);
        expected_guadeloupe.setConfirmed_dates(expected_guadeloupe_confirmed_dates);
        LinkedHashMap<String, Integer> expected_guadeloupe_deaths_dates = new LinkedHashMap<>();
        expected_guadeloupe_deaths_dates.put("2022-04-20", 941);
        expected_guadeloupe_deaths_dates.put("2022-04-19", 941);
        expected_guadeloupe_deaths_dates.put("2022-04-18", 940);
        expected_guadeloupe.setDeaths_dates(expected_guadeloupe_deaths_dates);
        expected_france_territories.add(expected_guadeloupe);

        expected_france.setTerritories(expected_france_territories);

        Country expected_spain = new Country();
        All expected_spain_all = new All();
        expected_spain_all.setCountry("Spain");
        expected_spain_all.setAbbreviation("ES");
        LinkedHashMap<String, Integer> expected_spain_confirmed_dates = new LinkedHashMap<>();
        expected_spain_confirmed_dates.put("2022-04-20", 11736893);
        expected_spain_confirmed_dates.put("2022-04-19", 11736893);
        expected_spain_confirmed_dates.put("2022-04-18", 11662214);
        LinkedHashMap<String, Integer> expected_spain_deaths_dates = new LinkedHashMap<>();
        expected_spain_deaths_dates.put("2022-04-20", 103721);
        expected_spain_deaths_dates.put("2022-04-19", 103104);
        expected_spain_deaths_dates.put("2022-04-18", 103104);
        expected_spain_all.setConfirmed_dates(expected_spain_confirmed_dates);
        expected_spain_all.setDeaths_dates(expected_spain_deaths_dates);
        expected_spain.setAll(expected_spain_all);

        ArrayList<Country> expected_countries = new ArrayList<>();
        expected_countries.add(expected_portugal);
        expected_countries.add(expected_france);
        expected_countries.add(expected_spain);

        // testing the service

        ArrayList<Country> actual_countries = service.getAll();

        assertThat(actual_countries.toString(), is(equalTo(expected_countries.toString())));
        Mockito.verify(mockClient).fetchMultipleCountries();

    }

    /* Test if the service can correctly process a list of countries' statuses. */
    @Test
    public void testGetAllCountriesStatus() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        ObjectMapper mapper = new ObjectMapper();

        String pt_response_confirmed_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 3757590, \"2022-04-19\": 3745569, \"2022-04-18\": 3719485}}}";
        String pt_response_deaths_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 22106, \"2022-04-19\": 22088, \"2022-04-18\": 21993}}}";

        ResponseEntity<String> pt_response_confirmed = new ResponseEntity<>(pt_response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> pt_response_deaths = new ResponseEntity<>(pt_response_deaths_str, HttpStatus.OK);
        JsonNode pt_country_confirmed = mapper.readTree(pt_response_confirmed.getBody());
        JsonNode pt_country_deaths = mapper.readTree(pt_response_deaths.getBody());
        JsonNode[] pt_mock_ret = { pt_country_confirmed, pt_country_deaths };

        String fr_response_confirmed_str = "{\"All\": {\"country\": \"France\", \"population\": 64979548, \"sq_km_area\": 551500, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 375, \"continent\": \"Europe\", \"abbreviation\": \"FR\", \"location\": \"Western Europe\", \"iso\": 250, \"capital_city\": \"Paris\", \"dates\": {\"2022-04-20\": 27239081, \"2022-04-19\": 27083370, \"2022-04-18\": 27062073}}, \"French Guiana\": {\"dates\": {\"2022-04-20\": 80035, \"2022-04-19\": 80035, \"2022-04-18\": 79529}}, \"French Polynesia\": {\"dates\": {\"2022-04-20\": 72621, \"2022-04-19\": 72621, \"2022-04-18\": 72596}}, \"Guadeloupe\": {\"dates\": {\"2022-04-20\": 145228, \"2022-04-19\": 145228, \"2022-04-18\": 142573}}}";
        String fr_response_deaths_str = "{\"All\": {\"country\": \"France\", \"population\": 64979548, \"sq_km_area\": 551500, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 375, \"continent\": \"Europe\", \"abbreviation\": \"FR\", \"location\": \"Western Europe\", \"iso\": 250, \"capital_city\": \"Paris\", \"dates\": {\"2022-04-20\": 141443, \"2022-04-19\": 141216, \"2022-04-18\": 141035}}, \"French Guiana\": {\"dates\": {\"2022-04-20\": 394, \"2022-04-19\": 394, \"2022-04-18\": 394}}, \"French Polynesia\": {\"dates\": {\"2022-04-20\": 648, \"2022-04-19\": 648, \"2022-04-18\": 648}}, \"Guadeloupe\": {\"dates\": {\"2022-04-20\": 941, \"2022-04-19\": 941, \"2022-04-18\": 940}}}";

        ResponseEntity<String> fr_response_confirmed = new ResponseEntity<>(fr_response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> fr_response_deaths = new ResponseEntity<>(fr_response_deaths_str, HttpStatus.OK);
        JsonNode fr_country_confirmed = mapper.readTree(fr_response_confirmed.getBody());
        JsonNode fr_country_deaths = mapper.readTree(fr_response_deaths.getBody());
        JsonNode[] fr_mock_ret = { fr_country_confirmed, fr_country_deaths };

        String es_response_confirmed_str = "{\"All\": {\"country\": \"Spain\", \"population\": 46354321, \"sq_km_area\": 505992, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 660, \"continent\": \"Europe\", \"abbreviation\": \"ES\", \"location\": \"Southern Europe\", \"iso\": 724, \"capital_city\": \"Madrid\", \"dates\": {\"2022-04-20\": 11736893, \"2022-04-19\": 11736893, \"2022-04-18\": 11662214}}}";
        String es_response_deaths_str = "{\"All\": {\"country\": \"Spain\", \"population\": 46354321, \"sq_km_area\": 505992, \"life_expectancy\": \"78.8\", \"elevation_in_meters\": 660, \"continent\": \"Europe\", \"abbreviation\": \"ES\", \"location\": \"Southern Europe\", \"iso\": 724, \"capital_city\": \"Madrid\", \"dates\": {\"2022-04-20\": 103721, \"2022-04-19\": 103104, \"2022-04-18\": 103104}}}";

        ResponseEntity<String> es_response_confirmed = new ResponseEntity<>(es_response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> es_response_deaths = new ResponseEntity<>(es_response_deaths_str, HttpStatus.OK);
        JsonNode es_country_confirmed = mapper.readTree(es_response_confirmed.getBody());
        JsonNode es_country_deaths = mapper.readTree(es_response_deaths.getBody());
        JsonNode[] es_mock_ret = { es_country_confirmed, es_country_deaths };

        ArrayList<JsonNode[]> mock_ret = new ArrayList<>();
        mock_ret.add(pt_mock_ret);
        mock_ret.add(fr_mock_ret);
        mock_ret.add(es_mock_ret);

        when(mockClient.fetchMultipleCountries()).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_portugal = new Country();
        All expected_portugal_all = new All();
        expected_portugal_all.setCountry("Portugal");
        expected_portugal_all.setAbbreviation("PT");
        LinkedHashMap<String, Integer> expected_portugal_confirmed_dates = new LinkedHashMap<>();
        expected_portugal_confirmed_dates.put("2022-04-20", 3757590);
        expected_portugal_confirmed_dates.put("2022-04-19", 3745569);
        expected_portugal_confirmed_dates.put("2022-04-18", 3719485);
        LinkedHashMap<String, Integer> expected_portugal_deaths_dates = null;
        expected_portugal_all.setConfirmed_dates(expected_portugal_confirmed_dates);
        expected_portugal_all.setDeaths_dates(expected_portugal_deaths_dates);
        expected_portugal.setAll(expected_portugal_all);

        Country expected_france = new Country();

        All expected_france_all = new All();
        expected_france_all.setCountry("France");
        expected_france_all.setAbbreviation("FR");
        LinkedHashMap<String, Integer> expected_france_confirmed_dates = new LinkedHashMap<>();
        expected_france_confirmed_dates.put("2022-04-20", 27239081);
        expected_france_confirmed_dates.put("2022-04-19", 27083370);
        expected_france_confirmed_dates.put("2022-04-18", 27062073);
        LinkedHashMap<String, Integer> expected_france_deaths_dates = null;
        expected_france_all.setConfirmed_dates(expected_france_confirmed_dates);
        expected_france_all.setDeaths_dates(expected_france_deaths_dates);
        expected_france.setAll(expected_france_all);

        ArrayList<Territory> expected_france_territories = new ArrayList<>();

        Territory expected_french_guiana = new Territory();
        expected_french_guiana.setTerritory("French Guiana");
        LinkedHashMap<String, Integer> expected_french_guiana_confirmed_dates = new LinkedHashMap<>();
        expected_french_guiana_confirmed_dates.put("2022-04-20", 80035);
        expected_french_guiana_confirmed_dates.put("2022-04-19", 80035);
        expected_french_guiana_confirmed_dates.put("2022-04-18", 79529);
        expected_french_guiana.setConfirmed_dates(expected_french_guiana_confirmed_dates);
        LinkedHashMap<String, Integer> expected_french_guiana_deaths_dates = null;
        expected_french_guiana.setDeaths_dates(expected_french_guiana_deaths_dates);
        expected_france_territories.add(expected_french_guiana);

        Territory expected_french_polynesia = new Territory();
        expected_french_polynesia.setTerritory("French Polynesia");
        LinkedHashMap<String, Integer> expected_french_polynesia_confirmed_dates = new LinkedHashMap<>();
        expected_french_polynesia_confirmed_dates.put("2022-04-20", 72621);
        expected_french_polynesia_confirmed_dates.put("2022-04-19", 72621);
        expected_french_polynesia_confirmed_dates.put("2022-04-18", 72596);
        expected_french_polynesia.setConfirmed_dates(expected_french_polynesia_confirmed_dates);
        LinkedHashMap<String, Integer> expected_french_polynesia_deaths_dates = null;
        expected_french_polynesia.setDeaths_dates(expected_french_polynesia_deaths_dates);
        expected_france_territories.add(expected_french_polynesia);

        Territory expected_guadeloupe = new Territory();
        expected_guadeloupe.setTerritory("Guadeloupe");
        LinkedHashMap<String, Integer> expected_guadeloupe_confirmed_dates = new LinkedHashMap<>();
        expected_guadeloupe_confirmed_dates.put("2022-04-20", 145228);
        expected_guadeloupe_confirmed_dates.put("2022-04-19", 145228);
        expected_guadeloupe_confirmed_dates.put("2022-04-18", 142573);
        expected_guadeloupe.setConfirmed_dates(expected_guadeloupe_confirmed_dates);
        LinkedHashMap<String, Integer> expected_guadeloupe_deaths_dates = null;
        expected_guadeloupe.setDeaths_dates(expected_guadeloupe_deaths_dates);
        expected_france_territories.add(expected_guadeloupe);

        expected_france.setTerritories(expected_france_territories);

        Country expected_spain = new Country();
        All expected_spain_all = new All();
        expected_spain_all.setCountry("Spain");
        expected_spain_all.setAbbreviation("ES");
        LinkedHashMap<String, Integer> expected_spain_confirmed_dates = new LinkedHashMap<>();
        expected_spain_confirmed_dates.put("2022-04-20", 11736893);
        expected_spain_confirmed_dates.put("2022-04-19", 11736893);
        expected_spain_confirmed_dates.put("2022-04-18", 11662214);
        LinkedHashMap<String, Integer> expected_spain_deaths_dates = null;
        expected_spain_all.setConfirmed_dates(expected_spain_confirmed_dates);
        expected_spain_all.setDeaths_dates(expected_spain_deaths_dates);
        expected_spain.setAll(expected_spain_all);

        ArrayList<Country> expected_countries = new ArrayList<>();
        expected_countries.add(expected_portugal);
        expected_countries.add(expected_france);
        expected_countries.add(expected_spain);

        // testing the service

        ArrayList<Country> actual_countries = service.getStatus("confirmed");

        assertThat(actual_countries.toString(), is(equalTo(expected_countries.toString())));
        Mockito.verify(mockClient).fetchMultipleCountries();

    }

    /* Test if the service can correctly make use of the cache. */
    @Test
    public void testCacheUse() throws JsonMappingException, JsonProcessingException {

        // preparing mock response

        String response_confirmed_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 3757590, \"2022-04-19\": 3745569, \"2022-04-18\": 3719485}}}";
        String response_deaths_str = "{\"All\": {\"country\": \"Portugal\", \"population\": 10329506, \"sq_km_area\": 91982, \"life_expectancy\": \"75.8\", \"elevation_in_meters\": 372, \"continent\": \"Europe\", \"abbreviation\": \"PT\", \"location\": \"Southern Europe\", \"iso\": 620, \"capital_city\": \"Lisboa\", \"dates\": {\"2022-04-20\": 22106, \"2022-04-19\": 22088, \"2022-04-18\": 21993}}}";

        ResponseEntity<String> response_confirmed = new ResponseEntity<>(response_confirmed_str, HttpStatus.OK);
        ResponseEntity<String> response_deaths = new ResponseEntity<>(response_deaths_str, HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode country_confirmed = mapper.readTree(response_confirmed.getBody());
        JsonNode country_deaths = mapper.readTree(response_deaths.getBody());
        JsonNode[] mock_ret = { country_confirmed, country_deaths };

        when(mockClient.fetchOneCountry("Portugal", false)).thenReturn(mock_ret);

        // preparing correct solution

        Country expected_portugal = new Country();
        All expected_portugal_all = new All();
        expected_portugal_all.setCountry("Portugal");
        expected_portugal_all.setAbbreviation("PT");
        LinkedHashMap<String, Integer> expected_portugal_confirmed_dates = new LinkedHashMap<>();
        expected_portugal_confirmed_dates.put("2022-04-20", 3757590);
        expected_portugal_confirmed_dates.put("2022-04-19", 3745569);
        expected_portugal_confirmed_dates.put("2022-04-18", 3719485);
        LinkedHashMap<String, Integer> expected_portugal_deaths_dates = new LinkedHashMap<>();
        expected_portugal_deaths_dates.put("2022-04-20", 22106);
        expected_portugal_deaths_dates.put("2022-04-19", 22088);
        expected_portugal_deaths_dates.put("2022-04-18", 21993);
        expected_portugal_all.setConfirmed_dates(expected_portugal_confirmed_dates);
        expected_portugal_all.setDeaths_dates(expected_portugal_deaths_dates);
        expected_portugal.setAll(expected_portugal_all);

        // testing the service

        // by making two successive calls to the getCountry method, the service should
        // use
        // the cache to answer to the second one
        Country actual_portugal_external_api = service.getCountry("Portugal", false);
        Country actual_portugal_cache = service.getCountry("Portugal", false);

        // we assert that both answers (from the external API and from the cache) were
        // correct
        assertThat(actual_portugal_external_api.toString(), is(equalTo(expected_portugal.toString())));
        assertThat(actual_portugal_cache.toString(), is(equalTo(expected_portugal.toString())));

        // we verify that the mock was only called once, since the cache handled the
        // second request
        Mockito.verify(mockClient).fetchOneCountry("Portugal", false);

    }

    @AfterEach
    public void cleanUp() {
        this.service = null;
        this.cache.erase();
    }

}
