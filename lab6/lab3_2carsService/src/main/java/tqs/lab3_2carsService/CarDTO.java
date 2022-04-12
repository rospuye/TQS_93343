package tqs.lab3_2carsService;

public class CarDTO {

    static final int MAX_MAKER_AND_MODEL_SIZE = 30;

    private Long carId;
    private String maker;
    private String model;

    public static CarDTO fromCarEntity(Car car) {
        return new CarDTO(car.getCarId(), car.getMaker(), car.getModel());
    }

    public Car toCarEntity() {
        Car car = new Car(this.getMaker(), this.getModel());
        car.setCarId(this.getCarId());
        return car;
    }

    public CarDTO() {

    }

    public CarDTO(Long carId, String maker, String model) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
