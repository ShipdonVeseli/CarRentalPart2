package com.example.carrentalcars.service;

import com.example.carrentalcars.ArrayOfdouble;
import com.example.carrentalcars.ConvertCurrencyListResponse;
import com.example.carrentalcars.client.CurrencyClient;
import com.example.carrentalcars.entity.Car;
import com.example.carrentalcars.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {
    private CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    private CurrencyClient currencyClient;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public CarService(CarRepository carRepository, RabbitTemplate rabbitTemplate, CurrencyClient currencyClient) {
        this.carRepository = carRepository;
        this.currencyClient = currencyClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "removeCar.queue")
    public String removeCarFromUser(String message) {
        logger.info("Received with userid: {}", message);
        String[] parts = message.split(",");
        Car updatedcar = carRepository.findById(parts[1]);
        if(updatedcar.getUserid().equals(parts[0])) {
            updatedcar.setUserid("0");
            carRepository.save(updatedcar);
            return "successful";
        }
        return "";
    }

    @RabbitListener(queues = "addCar.queue")
    public String addCarToUser(String message) {
        try {
            logger.info("Received with userid: {}", message);
            String[] parts = message.split(",");
            Car updatedcar = carRepository.findById(parts[1]);
            if (updatedcar.getUserid().equals("0")) {
                updatedcar.setUserid(parts[0]);
                carRepository.save(updatedcar);
                return "successful";
            }
        } catch(Exception e) {
            e.getMessage();
        }
        return "";
    }

    public String checkIfUserExists(String userId) {
        return (String) rabbitTemplate.convertSendAndReceive("user.exchange", "auth.routingKey", userId);
    }

    public Car createNewCar(Car car) {
        car.setId(UUID.randomUUID().toString());
        return carRepository.save(car);
    }

    public List<Car> getAllCars(String currency) {
        List<Car> cars;
        return convertCarCurrencies(carRepository.findAll(), currency);
    }

    private List<Car> convertCarCurrencies(List<Car> cars, String currency){
        if(currency.equals("USD")) return cars;
        ArrayOfdouble carprices = new ArrayOfdouble();
        for(int i = 0; i < cars.size(); i++){
            carprices.getDouble().add(cars.get(i).getDayPrice());
        }
        ConvertCurrencyListResponse response = currencyClient.convertCurrencyListResponse(carprices,"USD", currency);
        for(int i = 0; i < cars.size(); i++){
            cars.get(i).setDayPrice(response.getConvertCurrencyListResult().getValue().getDouble().get(i));
        }
        return cars;
    }
    
    public Car getCar(String id) { return carRepository.findById(id); }

    public List<Car> getCarsByUserId(String id, String currency) {
        return convertCarCurrencies(carRepository.findByUserid(id), currency);

    }

}
