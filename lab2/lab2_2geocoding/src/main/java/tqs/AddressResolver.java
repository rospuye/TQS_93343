package tqs;

import java.util.Optional;

public class AddressResolver {

    private ISimpleHttpClient httpClient;
    
    public AddressResolver(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Address> findAddressForLocation(double latitude, double longitude) {
        String addressStr = this.httpClient.doHttpGet(String.valueOf(latitude) + ", " + String.valueOf(longitude));
        String[] params = addressStr.split(", ");
        Address address = new Address(params[0], params[1], params[2], params[3], params[4]);
        Optional<Address> opt = Optional.ofNullable(address);
        return opt;
    }

}
