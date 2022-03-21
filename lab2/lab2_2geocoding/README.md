# 2.2 Geocoding

### b)

The SuT would be the AddressResolver class, specifically the behavior of its findAddressForLocation method. The service to mock would be the remote geocoding service.

# 2.3 Integration

Using the `mvn test` and the `mvn install failsafe:integration-test` commands, the results are different. The first command runs only the unit tests, unlike the second command, which runs those as well as the integration tests. That being the case, it makes sense that the second command would take longer to complete, as integration tests take longer than unit tests to run (there is added complexity due to the involvement of more parties).