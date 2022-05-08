package com.example.carrentalcars.entity;



import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cars")
@Getter
@Setter
public class Car {

    @Id
    private String id;

    private int availableSeats;

    private String transmission;

    private double dayPrice;

    private String userid;



    public Car(String id, int availableSeats, String transmission, double dayPrice, String userid) {
        super();
        this.id = id;
        this.availableSeats = availableSeats;
        this.transmission = transmission;
        this.dayPrice = dayPrice;
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", availableSeats=" + availableSeats +
                ", transmission='" + transmission + '\'' +
                ", dayPrice=" + dayPrice +
                ", userid='" + userid + '\'' +
                '}';
    }
}
