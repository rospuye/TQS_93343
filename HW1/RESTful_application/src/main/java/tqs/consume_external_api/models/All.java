package tqs.consume_external_api.models;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class All {

    private String country;
    private String abbreviation;
    private HashMap<String,Integer> confirmed_dates;
    private HashMap<String,Integer> deaths_dates;

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

    public HashMap<String, Integer> getConfirmed_dates() {
        return confirmed_dates;
    }

    public void setConfirmed_dates(HashMap<String, Integer> confirmed_dates) {
        this.confirmed_dates = confirmed_dates;
    }

    public HashMap<String, Integer> getDeaths_dates() {
        return deaths_dates;
    }

    public void setDeaths_dates(HashMap<String, Integer> deaths_dates) {
        this.deaths_dates = deaths_dates;
    }

    public All clone() {
        All al = new All();
        al.setCountry(country);
        al.setAbbreviation(abbreviation);
        al.setConfirmed_dates(new HashMap<String,Integer>(confirmed_dates));
        al.setDeaths_dates(new HashMap<String,Integer>(deaths_dates));
        return al;
    }

    @Override
    public String toString() {
        String ret = "All [country=" + country + ", abbreviation=" + abbreviation; 
        if (!confirmed_dates.isEmpty()) {
            ret += ", confirmed_dates=" + confirmed_dates;
        }
        if (!deaths_dates.isEmpty()) {
            ret += ", deaths_dates=" + deaths_dates;
        }
        ret += "]";
        return ret;
    }
    
}
