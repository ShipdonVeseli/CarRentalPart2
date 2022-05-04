package com.example.carrentalcars.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cars")
public class Car {

    @Id
    private Long id;

    private int availableSeats;

    private String transmission;

    private double dayPrice;

    private Long userid;



    public Car(Long id, int availableSeats, String transmission, double dayPrice, Long userid) {
        super();
        this.id = id;
        this.availableSeats = availableSeats;
        this.transmission = transmission;
        this.dayPrice = dayPrice;
        this.userid = userid;
    }
}
