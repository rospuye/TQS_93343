package tqs.lab3_2carsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.lab3_2carsService.Car;
import tqs.lab3_2carsService.CarManagerService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarManagerService carService;

    public CarController(CarManagerService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars" )
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.save(car);
        return new ResponseEntity<>(saved, status);
    }
    
    @GetMapping(path="/cars" )
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
        
    @GetMapping(path="/cars/{carId}" )
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        Optional<Car> car = carService.getCarDetails(carId);
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }

}
