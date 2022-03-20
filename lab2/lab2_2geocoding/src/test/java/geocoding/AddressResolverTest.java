package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    TqsBasicHttpClient mockClient = mock(TqsBasicHttpClient.class);

    //private ISimpleHttpClient iSimpleHttpClient;
    private AddressResolver addressResolver;

    @BeforeEach
    void setUp() {
        this.addressResolver = new AddressResolver(mockClient);
    }

    @Test
    void whenResolveAlboiGps_returnCaisAlboiAddress() throws ParseException, IOException, URISyntaxException {

        // coordinates for this response: 40.638963,-8.651836
        String staticResponse = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.638963,\"lng\":-8.651836}},\"locations\":[{\"street\":\"Rua Combatentes da Grande Guerra\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-087\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.638696,\"lng\":-8.651937},\"displayLatLng\":{\"lat\":40.638696,\"lng\":-8.651937},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.638696484940525,-8.651937143246526|marker-sm-50318A-1&scalebar=true&zoom=15&rand=1267916163\"}]}]}";

        when(mockClient.doHttpGet(any(String.class))).thenReturn(staticResponse);

        Optional<Address> result = addressResolver.findAddressForLocation(40.638963,-8.651836);
        Address address = result.get();
        Address staticResponseAddress = new Address("Rua Combatentes da Grande Guerra", "Glória e Vera Cruz", "Centro", "3810-087", null);

        assertEquals(address, staticResponseAddress);
        Mockito.verify(mockClient).doHttpGet(any(String.class));

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        // coordinates for this response: 200,200 (invalid)
        String staticResponse = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":200.0,\"lng\":200.0}},\"locations\":[]}]}";

        when(mockClient.doHttpGet(any(String.class))).thenReturn(staticResponse);

        Optional<Address> result = addressResolver.findAddressForLocation(200,200);

        assertEquals(result, Optional.empty());
        Mockito.verify(mockClient).doHttpGet(any(String.class));

    }

    @AfterEach
    void cleanUp() {
        this.addressResolver = null;
    }
}