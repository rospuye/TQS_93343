package tqs.lab3_2carsService;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tqs_car")
public class Car {

    static final int MAX_MAKER_AND_MODEL_SIZE = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;

    @Size(min = 3, max = MAX_MAKER_AND_MODEL_SIZE)
    private String maker;

    
    @Size(min = 3, max = MAX_MAKER_AND_MODEL_SIZE)
    private String model;

    public Car() {}

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((carId == null) ? 0 : carId.hashCode());
        result = prime * result + ((maker == null) ? 0 : maker.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (carId == null) {
            if (other.carId != null)
                return false;
        } else if (!carId.equals(other.carId))
            return false;
        if (maker == null) {
            if (other.maker != null)
                return false;
        } else if (!maker.equals(other.maker))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        return true;
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

    @Override
    public String toString() {
        return "Car [carId=" + carId + ", maker=" + maker + ", model=" + model + "]";
    }

}