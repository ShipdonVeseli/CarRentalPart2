package com.example.carrentalcars.exception;

import java.text.MessageFormat;

public class CarIsAlreadyAssignedException extends RuntimeException {
    public CarIsAlreadyAssignedException(final Long carId) {
        super(MessageFormat.format("Car {0} is already assigned.", carId));
    }
}
