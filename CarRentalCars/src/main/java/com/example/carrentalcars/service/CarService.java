package com.example.carrentalcars.service;

import com.example.carrentalcars.ArrayOfdouble;
import com.example.carrentalcars.ConvertCurrencyListResponse;
import com.example.carrentalcars.client.CurrencyClient;
import com.example.carrentalcars.entity.Car;

import com.example.carrentalcars.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarService {
    private CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    private CurrencyClient currencyClient = new CurrencyClient();
    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public String receivedMessage(String message) {
        logger.info("Received with userid: {}", message);
        String[] parts = message.split(",");
        if(parts[0].equals("add")){
            Car updatedcar = carRepository.findById(parts[2]);
            if(updatedcar.getUserid().equals("0")) {
                updatedcar.setUserid(parts[1]);
                carRepository.save(updatedcar);
                return "successful";
            }
        }else if(parts[0].equals("remove")){
            Car updatedcar = carRepository.findById(parts[2]);
            updatedcar.setUserid("0");
            carRepository.save(updatedcar);
            return "successful";
        }
        return "";
    }

    public Car createNewCar(Car car) {
        car.setId(UUID.randomUUID().toString());
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        cars = carRepository.findAll();
        ArrayOfdouble carprices = new ArrayOfdouble();
        for(int i = 0; i < cars.size(); i++){
            carprices.getDouble().add(cars.get(i).getDayPrice());
        }
        ConvertCurrencyListResponse response = currencyClient.convertCurrencyListResponse(carprices,"USD", "TRY");
        for(int i = 0; i < cars.size(); i++){
            cars.get(i).setDayPrice(response.getConvertCurrencyListResult().getValue().getDouble().get(i));
        }
        return cars;
    }
    
    public Car getCar(String id) { return carRepository.findById(id); }

    public List<Car> getCarsByUserId(String id) { return carRepository.findByUserid(id); }

}
