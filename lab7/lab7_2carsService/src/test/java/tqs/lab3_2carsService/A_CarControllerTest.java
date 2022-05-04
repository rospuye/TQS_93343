package tqs.lab3_2carsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.lab3_2carsService.CarController;
import tqs.lab3_2carsService.Car;
import tqs.lab3_2carsService.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class A_CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    // test createCar(Car car)
    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car nissan = new Car("nissan", "qashqai");

        when(service.save(Mockito.any())).thenReturn(nissan);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(nissan)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("nissan")))
                .andExpect(jsonPath("$.model", is("qashqai")));

        verify(service, times(1)).save(Mockito.any());

    }

    // test getAllCars()
    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car nissan = new Car("nissan", "qashqai");
        Car citroen = new Car("citroen", "ds4");
        Car opel = new Car("opel", "astra");

        List<Car> allCars = Arrays.asList(nissan, citroen, opel);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(nissan.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(citroen.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(opel.getMaker())))
                .andExpect(jsonPath("$[0].model", is(nissan.getModel())))
                .andExpect(jsonPath("$[1].model", is(citroen.getModel())))
                .andExpect(jsonPath("$[2].model", is(opel.getModel())));
        verify(service, times(1)).getAllCars();
    }

    // test getCarById(@PathVariable Long carId)
    @Test
    void givenCar_whenGetCar_thenReturnCar() throws Exception {
        Optional<Car> nissan = Optional.of(new Car("nissan", "qashqai"));
        Long id = 0L;

        when(service.getCarDetails(id)).thenReturn(nissan);

        mvc.perform(
            get("/api/cars/{id}", "0").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.maker", is("nissan")))
            .andExpect(jsonPath("$.model", is("qashqai")));
        verify(service, times(1)).getCarDetails(id);
    }
    
}
