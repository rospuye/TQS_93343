package tqs;

public class ISimpleHttpClient {

    private TqsBasicHttpClient httpClient;
    
    public String doHttpGet(String coords) {
        return this.httpClient.doHttpGet(coords);
    }

    public void setHttpClient(TqsBasicHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
