package tqs.consume_external_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import tqs.consume_external_api.cache.Cache;
import tqs.consume_external_api.cache.CacheItem;
import tqs.consume_external_api.models.All;
import tqs.consume_external_api.models.Country;
import tqs.consume_external_api.models.Territory;

public class CountryService {

    private Cache cache;
    private static final Logger log = LoggerFactory.getLogger(CountryService.class);
    private CountryClient client;

    public CountryService(CountryClient client) {
        cache = Cache.getInstance();
        this.client = client;
    }

    public String getCacheStats() {
        return cache.toString();
    }

    public ArrayList<Country> getAll() throws JsonMappingException, JsonProcessingException {
        if (cache.size() >= 195) {
            ArrayList<CacheItem> cached_countries = cache.getAllCountries();
            ArrayList<Country> countries = new ArrayList<>();
            for (CacheItem item : cached_countries) {
                countries.add(item.getCountry());
            }
            log.info("SERVICE: retrieved all countries from cache.");
            return countries;
        }

        ArrayList<JsonNode[]> countries_data = client.fetchMultipleCountries();
        ArrayList<Country> countries = new ArrayList<>();

        for (JsonNode[] country_data : countries_data) {
            Country country = processCountry(country_data[0], country_data[1]);
            if (country != null) {
                countries.add(country);
                CacheItem item = new CacheItem(country.getAll().getCountry(), country.getAll().getAbbreviation(), country);
                cache.addItem(item);
            }
        }

        log.info("SERVICE: retrieved all countries from external API.");
        return countries;
    }

    public Country getCountry(String name, boolean ab) throws JsonMappingException, JsonProcessingException {
        if (cache.containsItem(name)) {
            log.info("SERVICE: retrieved " + name + " from cache.");
            return cache.getItem(name).getCountry();
        }
        JsonNode[] country_data = client.fetchOneCountry(name, ab);
        Country country = processCountry(country_data[0], country_data[1]);
        CacheItem item = new CacheItem(country.getAll().getCountry(), country.getAll().getAbbreviation(), country);
        cache.addItem(item);
        log.info("SERVICE: retrieved " + name + " from external API.");
        return country;
    }

    public ArrayList<Country> getStatus(String status) throws JsonMappingException, JsonProcessingException {
        ArrayList<Country> countries = this.getAll();
        ArrayList<Country> new_countries = new ArrayList<Country>(countries);
        for (Country country : new_countries) {
            if (status.equals("confirmed")) {
                country.getAll().setDeaths_dates(null);
                for (Territory territory : country.getTerritories()) {
                    territory.setDeaths_dates(null);
                }
            } else if (status.equals("deaths")) {
                country.getAll().setConfirmed_dates(null);
                for (Territory territory : country.getTerritories()) {
                    territory.setConfirmed_dates(null);
                }
            }
        }
        log.info("SERVICE: retrieved all countries' status (" + status + ").");
        return new_countries;
    }

    public Country getCountryStatus(String country_name, String status, boolean ab)
            throws JsonMappingException, JsonProcessingException {
        Country country = this.getCountry(country_name, ab).clone();
        if (status.equals("confirmed")) {
            country.getAll().setDeaths_dates(null);
            for (Territory territory : country.getTerritories()) {
                territory.setDeaths_dates(null);
            }
        } else if (status.equals("deaths")) {
            country.getAll().setConfirmed_dates(null);
            for (Territory territory : country.getTerritories()) {
                territory.setConfirmed_dates(null);
            }
        }
        log.info("SERVICE: retrieved " + country_name + "'s status (" + status + ").");
        return country;
    }

    // ---------------------

    private Country processCountry(JsonNode country_confirmed, JsonNode country_deaths) {

        Country country = new Country();
        All all = new All();
        ArrayList<Territory> territories = new ArrayList<>();

        JsonNode all_confirmed = country_confirmed.get("All");
        JsonNode all_deaths = country_deaths.get("All");

        try {
            String country_name = all_confirmed.get("country").toString().replace("\"", "");
            String country_ab = all_confirmed.get("abbreviation").toString().replace("\"", "");

            LinkedHashMap<String, Integer> confirmed_dates = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> deaths_dates = new LinkedHashMap<>();

            JsonNode confirmed_dates_json = all_confirmed.get("dates");
            JsonNode deaths_dates_json = all_deaths.get("dates");

            Iterator<String> confirmed_dates_iterator = confirmed_dates_json.fieldNames();
            confirmed_dates_iterator.forEachRemaining((date) -> {
                if (confirmed_dates.size() < 3) { // collect only 3 most recent data points
                    JsonNode value = confirmed_dates_json.get(date);
                    Integer value_int = Integer.parseInt(value.toString());
                    confirmed_dates.put(date, value_int);
                }
            });

            Iterator<String> deaths_dates_iterator = deaths_dates_json.fieldNames();
            deaths_dates_iterator.forEachRemaining((date) -> {
                if (deaths_dates.size() < 3) { // collect only 3 most recent data points
                    JsonNode value = deaths_dates_json.get(date);
                    Integer value_int = Integer.parseInt(value.toString());
                    deaths_dates.put(date, value_int);
                }
            });

            all.setCountry(country_name);
            all.setAbbreviation(country_ab);
            all.setConfirmed_dates(confirmed_dates);
            all.setDeaths_dates(deaths_dates);

            country.setAll(all);

            Iterator<String> territories_iterator = country_confirmed.fieldNames();
            territories_iterator.forEachRemaining((territory_name) -> {
                if (!territory_name.equals("All")) {

                    Territory territory = new Territory();

                    LinkedHashMap<String, Integer> confirmed_dates_territory = new LinkedHashMap<>();
                    LinkedHashMap<String, Integer> deaths_dates_territory = new LinkedHashMap<>();

                    JsonNode confirmed_dates_territory_json = country_confirmed.get(territory_name).get("dates");
                    JsonNode deaths_dates_territory_json = country_deaths.get(territory_name).get("dates");

                    Iterator<String> confirmed_dates_territory_iterator = confirmed_dates_territory_json.fieldNames();
                    confirmed_dates_territory_iterator.forEachRemaining((confirmed_date) -> {
                        if (confirmed_dates_territory.size() < 3) {
                            JsonNode value = confirmed_dates_territory_json.get(confirmed_date);
                            Integer value_int = Integer.parseInt(value.toString());
                            confirmed_dates_territory.put(confirmed_date, value_int);
                        }
                    });

                    Iterator<String> deaths_dates_territory_iterator = deaths_dates_territory_json.fieldNames();
                    deaths_dates_territory_iterator.forEachRemaining((deaths_date) -> {
                        if (deaths_dates_territory.size() < 3) {
                            JsonNode value = deaths_dates_territory_json.get(deaths_date);
                            Integer value_int = Integer.parseInt(value.toString());
                            deaths_dates_territory.put(deaths_date, value_int);
                        }
                    });

                    territory.setTerritory(territory_name);
                    territory.setConfirmed_dates(confirmed_dates_territory);
                    territory.setDeaths_dates(deaths_dates_territory);

                    territories.add(territory);

                }
            });

            country.setTerritories(territories);

            return country;

        } catch (Exception e) {
            return null;
        }

    }

}
