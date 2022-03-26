# Employee manager example

### a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

In the `C_EmployeeController_WithMockServiceTest.java` file, we can see a couple of examples of AssertJ expressive methods chaining, such as in the `whenPostEmployee_thenCreateEmployee` test, where the following code is present:

```
mvc.perform(
        post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(alex)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("alex")));
```

In the `D_EmployeeRestControllerIT.java` file, in the `whenValidInput_thenCreateEmployee` test, we can see:

```
mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(bob)));
```

### b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

In the `B_EmployeeService_UnitTest.java` file, we use a mock repository by doing

```
@Mock(lenient = true)
private EmployeeRepository employeeRepository;
```

and, thus, do not use a database.

### c) What is the difference between standard @Mock and @MockBean?

The `@MockBean` is used to **add mock objects to the Spring application context**. The mock will replace any existing bean of the same type in the application context. If no bean of the same type is defined, a new one will be added. This annotation is useful in integration tests where a particular bean, like an external service, needs to be mocked.

The `@Mock` annotation is appropriate for when we're testing something that does not need any dependencies from the Spring Boot container, since it favors the isolation of the tested component.

### d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

This file allows us to override whichever properties we wish in Spring's tests, and should be used for when we wish to run integration tests.

### e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

The strategy used in C makes use of an API provided by MockMvc. Since we are purely mocking the behavior of an API server towards our client, no actual database is involved. We just establish what the correct responses to each request should be, and use that to test our data access component.

In D, we use a servlet entry point. This servlet is used to extend the capabilities of an API server, so we are not yet testing the full data access process. However, it is no longer a mock, and there is a database involved in the process.

In E, we use an actual API client, thus testing the complete process of data retrieval, and so we will need to marshal and unmarshal the requests and responses.