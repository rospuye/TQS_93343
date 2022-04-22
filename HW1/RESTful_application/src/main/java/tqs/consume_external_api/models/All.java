package tqs.consume_external_api.models;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

@JsonIgnoreProperties(ignoreUnknown = true)
public class All {

    private String country = "";
    private String abbreviation = "";
    private LinkedHashMap<String,Integer> confirmed_dates;
    private LinkedHashMap<String,Integer> deaths_dates;

    public All() {}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public LinkedHashMap<String, Integer> getConfirmed_dates() {
        return confirmed_dates;
    }

    public void setConfirmed_dates(LinkedHashMap<String, Integer> confirmed_dates) {
        this.confirmed_dates = confirmed_dates;
    }

    public LinkedHashMap<String, Integer> getDeaths_dates() {
        return deaths_dates;
    }

    public void setDeaths_dates(LinkedHashMap<String, Integer> deaths_dates) {
        this.deaths_dates = deaths_dates;
    }

    public All clone() {
        All al = new All();
        al.setCountry(country);
        al.setAbbreviation(abbreviation);
        al.setConfirmed_dates(new LinkedHashMap<String,Integer>(confirmed_dates));
        al.setDeaths_dates(new LinkedHashMap<String,Integer>(deaths_dates));
        return al;
    }

    @Override
    public String toString() {
        String ret = "All [country=" + country + ", abbreviation=" + abbreviation; 
        if (confirmed_dates != null) {
            ret += ", confirmed_dates=" + new Gson().toJson(confirmed_dates, LinkedHashMap.class);
        }
        if (deaths_dates != null) {
            ret += ", deaths_dates=" + new Gson().toJson(deaths_dates, LinkedHashMap.class);
        }
        ret += "]";
        return ret;
    }
    
}
