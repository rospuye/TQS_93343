package tqs.consume_external_api.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String name) {
        super("Could not find country " + name);
    }

}
