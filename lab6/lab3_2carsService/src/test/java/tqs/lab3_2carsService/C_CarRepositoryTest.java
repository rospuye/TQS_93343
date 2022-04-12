package tqs.lab3_2carsService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.lab3_2carsService.Car;
import tqs.lab3_2carsService.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class C_CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarByExistingId_thenReturnCar() {
        Car car = new Car("testmaker", "testmodel");
        entityManager.persistAndFlush(car);

        Car fromDb = carRepository.findByCarId(car.getCarId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getMaker()).isEqualTo(car.getMaker());
        assertThat(fromDb.getModel()).isEqualTo(car.getModel());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-111L);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car nissan = new Car("nissan", "qashqai");
        Car citroen = new Car("citroen", "ds4");
        Car opel = new Car("opel", "astra");

        entityManager.persist(nissan);
        entityManager.persist(citroen);
        entityManager.persist(opel);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(nissan.getMaker(), citroen.getMaker(), opel.getMaker());
        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(nissan.getModel(), citroen.getModel(), opel.getModel());
    }

}
