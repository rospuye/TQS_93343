package tqs.lab3_2carsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.*;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.when;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest(CarController.class)
public class New_CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    // test createCar(Car car)
    @Test
    void whenPostCar_thenCreateCar() throws Exception {

        Car nissan = new Car("nissan", "qashqai");

        Mockito.when(service.save(Mockito.any())).thenReturn(nissan);

        RestAssuredMockMvc.given().contentType("application/json")
                .body(JsonUtils.toJson(nissan))
                .when()
                .post("/api/cars")
                .then().statusCode(201)
                .body("maker", is("nissan"))
                .body("model", is("qashqai"));

        Mockito.verify(service, times(1)).save(Mockito.any());

    }

    // test getAllCars()
    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car nissan = new Car("nissan", "qashqai");
        Car citroen = new Car("citroen", "ds4");
        Car opel = new Car("opel", "astra");

        List<Car> allCars = Arrays.asList(nissan, citroen, opel);

        Mockito.when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.given().contentType("application/json")
                .get("/api/cars")
                .then().statusCode(200)
                .body("$", hasSize(3))
                .body("[0].maker", is("nissan"))
                .body("[1].maker", is("citroen"))
                .body("[2].maker", is("opel"))
                .body("[0].model", is("qashqai"))
                .body("[1].model", is("ds4"))
                .body("[2].model", is("astra"));

        Mockito.verify(service, times(1)).getAllCars();
    }

    // test getCarById(@PathVariable Long carId)
    @Test
    void givenCar_whenGetCar_thenReturnCar() throws Exception {
        Optional<Car> nissan = Optional.of(new Car("nissan", "qashqai"));
        Long id = 0L;

        Mockito.when(service.getCarDetails(id)).thenReturn(nissan);

        RestAssuredMockMvc.given().contentType("application/json")
                .get("/api/cars/0")
                .then()
                .statusCode(200)
                .body("maker", equalTo("nissan"))
                .body("model", equalTo("qashqai"));

        Mockito.verify(service, times(1)).getCarDetails(id);
    }

}
