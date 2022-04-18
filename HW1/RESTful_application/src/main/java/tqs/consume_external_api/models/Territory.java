package tqs.consume_external_api.models;

import java.util.HashMap;

public class Territory {

    private String territory;
    private HashMap<String,Integer> confirmed_dates;
    private HashMap<String,Integer> deaths_dates;

    public Territory() {}

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
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

    public Territory clone() {
        Territory ter = new Territory();
        ter.setTerritory(this.territory);
        ter.setConfirmed_dates(new HashMap<String,Integer>(confirmed_dates));
        ter.setDeaths_dates(new HashMap<String,Integer>(deaths_dates));
        return ter;
    }

    @Override
    public String toString() {
        String ret = "Territory [territory=" + territory;
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
