package tqs.consume_external_api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import tqs.consume_external_api.cache.Cache;
import tqs.consume_external_api.cache.CacheItem;
import tqs.consume_external_api.models.All;
import tqs.consume_external_api.models.Country;

import java.util.concurrent.TimeUnit;

public class A_UnitTests {

    // ------------ TESTING THE CACHE ------------

    private Cache cache;

    @BeforeEach
    public void setupCache() {
        this.cache = Cache.getInstance();
    }

    @Test
    public void emptyOnConstruction()
    {   
        assertEquals(0, this.cache.size());
        assertTrue(this.cache.getAllCountries().isEmpty());
    }

    @Test
    public void whenInsertCountry_ThenCacheContainsCountry() {
        String country_name = "Portugal";
        String country_ab = "PT";
        All all = new All();
        all.setCountry(country_name);
        Country country = new Country();
        country.setAll(all);

        CacheItem item = new CacheItem(country_name, country_ab, country);
        this.cache.addItem(item);

        assertTrue(this.cache.containsItem(country_name));
        assertTrue(this.cache.containsItem(country_ab));

        assertEquals(this.cache.getItem(country_name),item);
        assertEquals(this.cache.getItem(country_ab),item);

        assertEquals(this.cache.size(),1);
    }

    @Test
    public void whenInsertMultipleCountries_ThenCacheContainsCountries() {

        String country_name1 = "Portugal";
        String country_ab1 = "PT";
        All all1 = new All();
        all1.setCountry(country_name1);
        Country country1 = new Country();
        country1.setAll(all1);

        String country_name2 = "Spain";
        String country_ab2 = "ES";
        All all2 = new All();
        all2.setCountry(country_name2);
        Country country2 = new Country();
        country2.setAll(all2);

        String country_name3 = "France";
        String country_ab3 = "FR";
        All all3 = new All();
        all3.setCountry(country_name3);
        Country country3 = new Country();
        country3.setAll(all3);

        CacheItem item1 = new CacheItem(country_name1, country_ab1, country1);
        CacheItem item2 = new CacheItem(country_name2, country_ab2, country2);
        CacheItem item3 = new CacheItem(country_name3, country_ab3, country3);
        this.cache.addItem(item1);
        this.cache.addItem(item2);
        this.cache.addItem(item3);

        ArrayList<CacheItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        assertTrue(this.cache.containsItem(country_name1));
        assertTrue(this.cache.containsItem(country_ab1));

        assertTrue(this.cache.containsItem(country_name2));
        assertTrue(this.cache.containsItem(country_ab2));

        assertTrue(this.cache.containsItem(country_name3));
        assertTrue(this.cache.containsItem(country_ab3));

        assertEquals(items,this.cache.getAllCountries());
        
        assertEquals(3,this.cache.size());
    }

    @Test
    public void whenInsertCountryAndWaitTTL_ThenCacheDoesntContainCountry() throws InterruptedException {
        String country_name = "Portugal";
        String country_ab = "PT";
        All all = new All();
        all.setCountry(country_name);
        Country country = new Country();
        country.setAll(all);

        CacheItem item = new CacheItem(country_name, country_ab, country);
        this.cache.addItem(item);

        TimeUnit.SECONDS.sleep(12); // extra 2 seconds for processing time

        assertFalse(this.cache.containsItem(country_name));
        assertFalse(this.cache.containsItem(country_ab));
        assertEquals(0,this.cache.size());
    }

    @Test
    public void whenInsertCountryAndRemoveIt_ThenCacheDoesntContainCountry() {
        String country_name = "Portugal";
        String country_ab = "PT";
        All all = new All();
        all.setCountry(country_name);
        Country country = new Country();
        country.setAll(all);

        CacheItem item = new CacheItem(country_name, country_ab, country);
        this.cache.addItem(item);
        this.cache.removeItem(item);

        assertFalse(this.cache.containsItem(country_name));
        assertFalse(this.cache.containsItem(country_ab));
        assertEquals(0,this.cache.size());
    }

    @Test
    public void whenInsertDuplicateCountry_ThenCacheContainsLastVersionOnly() {
        String country_name1 = "Portugal";
        String country_ab1 = "PT";
        All all1 = new All();
        all1.setCountry(country_name1);
        Country country1 = new Country();
        country1.setAll(all1);

        String country_ab2 = "PT2";
        All all2 = new All();
        all2.setCountry(country_name1);
        Country country2 = new Country();
        country2.setAll(all2);

        CacheItem item1 = new CacheItem(country_name1, country_ab1, country1);
        this.cache.addItem(item1);

        CacheItem item2 = new CacheItem(country_name1, country_ab2, country2);
        this.cache.addItem(item2);

        assertTrue(this.cache.containsItem(country_name1));
        assertTrue(this.cache.containsItem(country_ab2));
        assertEquals(1,this.cache.size());
    }

    @Test
    public void whenEmpty_ThenGetItemReturnsNull() {
        assertEquals(null,this.cache.getItem("Portugal"));
    }

    @AfterEach
    public void cleanUpCache() {
        this.cache.erase();
    }
    
}
