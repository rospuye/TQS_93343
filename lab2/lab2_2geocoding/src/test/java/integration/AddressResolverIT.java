package integration;

import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressResolverIT {

    TqsBasicHttpClient realClient = new TqsBasicHttpClient();

    private AddressResolver addressResolver;

    @BeforeEach
    void setUp() {
        this.addressResolver = new AddressResolver(realClient);
    }

    @Test
    void whenResolveAlboiGps_returnCaisAlboiAddress() throws ParseException, IOException, URISyntaxException {
        Optional<Address> result = addressResolver.findAddressForLocation(40.638963,-8.651836);
        Address address = result.get();
        Address staticResponseAddress = new Address("Rua Combatentes da Grande Guerra", "Glória e Vera Cruz", "Centro", "3810-087", null);

        assertEquals(address, staticResponseAddress);
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {
        Optional<Address> result = addressResolver.findAddressForLocation(200,200);
        assertEquals(result, Optional.empty());
    }

    @AfterEach
    void cleanUp() {
        this.addressResolver = null;
    }

}
