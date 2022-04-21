package tqs.consume_external_api.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private All all;
    private ArrayList<Territory> territories;

    public Country() {
        this.territories = new ArrayList<>();
    }

    public All getAll() {
        return all;
    }

    public void setAll(All all) {
        this.all = all;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        this.territories = territories;
    }
    
    public Country clone() {
        Country cou = new Country();
        cou.setAll(all.clone());
        ArrayList<Territory> ters = new ArrayList<>();
        for (Territory territory : territories) {
            Territory ter = territory.clone();
            ters.add(ter);
        }
        cou.setTerritories(ters);
        return cou;
    }

    @Override
    public String toString() {
        String ret = "Country [all=" + all; // + ", territories=" + territories + "]";
        if (territories!=null && territories.size()!=0) {
            ret += ", territories=" + territories;
        }
        ret += "]";
        return ret;
    }

}
