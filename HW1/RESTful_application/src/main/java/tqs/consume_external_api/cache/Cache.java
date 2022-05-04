package tqs.consume_external_api.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Cache {

    private static final Logger log = LoggerFactory.getLogger(Cache.class);

    private static Cache instance;

    private ArrayList<CacheItem> cache;
    private ArrayList<Integer> TTLs;
    private int request_count;
    private int get_successes;
    private int get_fails;
    private int put_successes;
    private int put_fails;
    private int delete_successes;
    private int delete_fails;
    private int contain_successes;
    private int contain_fails;
    private int size_requests;
    private Timer timer;

    private Cache() {
        this.cache = new ArrayList<>();
        this.TTLs = new ArrayList<>();
        this.request_count = 0;
        this.get_successes = 0;
        this.get_fails = 0;
        this.put_successes = 0;
        this.put_fails = 0;
        this.delete_successes = 0;
        this.delete_fails = 0;
        this.contain_successes = 0;
        this.contain_fails = 0;
        this.size_requests = 0;
        this.timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int ttl_ind=0; ttl_ind<TTLs.size(); ttl_ind++) {
                    int ttl = TTLs.get(ttl_ind);
                    if (ttl==0) {
                        CacheItem item = cache.get(ttl_ind);
                        cache.set(ttl_ind, null);
                        item = null;
                        TTLs.set(ttl_ind, null);
                    }
                    else { TTLs.set(ttl_ind, --ttl); }
                }
                // remove nulls from lists
                while (cache.remove(null)) { }
                while (TTLs.remove(null)) { }

                // System.out.println(instance);
            }
        // }, 0, 1000); // do it every second (cache keeps countries for 10 seconds)
        }, 0, 60000); // do it every minute (cache keeps countries for 10 minutes)

    }

    public static Cache getInstance() { // single Cache object
        if (instance == null) {
            instance = new Cache();
            return instance;
        }
        return instance;
    }

    public boolean addItem(CacheItem item) {
        if (cache != null) {
            this.request_count++;
            for (CacheItem ci : cache) {
                if (ci.getCountry().getAll().getCountry().equals(item.getCountry().getAll().getCountry())) { // no duplicate countries (replacement)
                    this.removeItem(ci);
                    break;
                }
            }
            boolean ret = cache.add(item);
            if (ret) {
                TTLs.add(10); // change TTL here
                put_successes++;
            } else {
                put_fails++;
            }
            return ret;
        }
        return false;
    }

    public boolean removeItem(CacheItem item) {
        if (cache != null) {
            this.request_count++;
            int item_ind = cache.indexOf(item);
            boolean ret = cache.remove(item);
            if (ret) {
                TTLs.set(item_ind, null);
                delete_successes++;
            } else {
                delete_fails++;
            }
            while (TTLs.remove(null)) { }
            return ret;
        }
        return false;
    }

    public CacheItem getItem(String id) {
        if (cache != null) {
            this.request_count++;
            for (CacheItem ci : cache) {
                if (ci.getCountry_name().equals(id) || ci.getAbbreviation().equals(id)) {
                    log.info("CACHE: get success.");
                    this.get_successes++;
                    return ci;
                }
            }
            log.info("CACHE: get fail.");
            this.get_fails++;
            return null;
        }
        return null;
    }

    public ArrayList<CacheItem> getAllCountries() {
        if (cache != null) {
            this.request_count++;
            log.info("CACHE: get success.");
            get_successes++;
            return cache;
        }
        log.info("CACHE: get fail.");
        get_fails++;
        return null;
    }

    public boolean containsItem(String id) {
        if (cache != null) {
            this.request_count++;
            for (CacheItem ci : cache) {
                if (ci.getCountry_name().equals(id) || ci.getAbbreviation().equals(id)) {
                    contain_successes++;
                    return true;
                }
            }
            contain_fails++;
            return false;
        }
        return false;
    }

    public int size() {
        this.request_count++;
        if (cache != null) {
            size_requests++;
            return cache.size();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Cache [contain_fails=" + contain_fails + ", contain_successes=" + contain_successes + ", delete_fails="
                + delete_fails + ", delete_successes=" + delete_successes + ", get_fails=" + get_fails
                + ", get_successes=" + get_successes + ", put_fails=" + put_fails + ", put_successes=" + put_successes
                + ", request_count=" + request_count + ", size_requests=" + size_requests + "]";
    }

    public void erase() {
        Cache.instance = null;
    }

}
