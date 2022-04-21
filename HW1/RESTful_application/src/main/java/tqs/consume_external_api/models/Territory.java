package tqs.consume_external_api.models;

import java.util.LinkedHashMap;

public class Territory {

    private String territory;
    private LinkedHashMap<String,Integer> confirmed_dates;
    private LinkedHashMap<String,Integer> deaths_dates;

    public Territory() {}

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
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

    public Territory clone() {
        Territory ter = new Territory();
        ter.setTerritory(this.territory);
        ter.setConfirmed_dates(new LinkedHashMap<String,Integer>(confirmed_dates));
        ter.setDeaths_dates(new LinkedHashMap<String,Integer>(deaths_dates));
        return ter;
    }

    @Override
    public String toString() {
        String ret = "Territory [territory=" + territory;
        if (confirmed_dates != null) {
            ret += ", confirmed_dates=" + confirmed_dates;
        }
        if (deaths_dates != null) {
            ret += ", deaths_dates=" + deaths_dates;
        }
        ret += "]";
        return ret;
    }

}
