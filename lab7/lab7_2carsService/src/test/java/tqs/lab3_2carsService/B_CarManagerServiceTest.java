package tqs.lab3_2carsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.lab3_2carsService.Car;
import tqs.lab3_2carsService.CarRepository;
import tqs.lab3_2carsService.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class B_CarManagerServiceTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    public void setUp() {

        Car nissan = new Car("nissan", "qashqai");
        nissan.setCarId(100L);

        Car citroen = new Car("citroen", "ds4");
        Car opel = new Car("opel", "astra");

        List<Car> allCars = Arrays.asList(nissan, citroen, opel);

        Mockito.when(carRepository.findByCarId(nissan.getCarId())).thenReturn(nissan);
        Mockito.when(carRepository.findByCarId(citroen.getCarId())).thenReturn(citroen);
        Mockito.when(carRepository.findByCarId(opel.getCarId())).thenReturn(opel);
        Mockito.when(carRepository.findByCarId(200L)).thenReturn(null);
        Mockito.when(carRepository.findById(nissan.getCarId())).thenReturn(Optional.of(nissan));
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void whenSearchValidId_thenCarShouldBeFound() {
        Long id = 100L;
        Optional<Car> found = carService.getCarDetails(id);

        if (found.isPresent()) {
            assertThat(found.get().getCarId()).isEqualTo(id);
        } else {
            fail();
        }
    }

    @Test
    void whenSearchInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDb = carService.getCarDetails(-99L);
        assertThat(fromDb).isEmpty();
        verifyFindByCarIdIsCalledOnce(-99L);
    }

    @Test
    void given3Cars_whengetAll_thenReturn3Records() {
        Car nissan = new Car("nissan", "qashqai");
        nissan.setCarId(100L);
        Car citroen = new Car("citroen", "ds4");
        Car opel = new Car("opel", "astra");

        List<Car> allCars = carService.getAllCars();

        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getCarId).contains(nissan.getCarId(), citroen.getCarId(),
            opel.getCarId());
    }

    private void verifyFindByCarIdIsCalledOnce(Long id) {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(id);
    }
    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

}
