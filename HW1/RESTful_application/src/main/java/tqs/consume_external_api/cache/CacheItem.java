package tqs.consume_external_api.cache;

import tqs.consume_external_api.models.Country;

public class CacheItem {

    private String country_name;
    private String abbreviation;
    private Country country;
    
    public CacheItem(String country_name, String abbreviation, Country country) {
        this.country_name = country_name;
        this.abbreviation = abbreviation;
        this.country = country;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "CacheItem [country=" + country + ", country_name=" + country_name + "]";
    }
    
}
