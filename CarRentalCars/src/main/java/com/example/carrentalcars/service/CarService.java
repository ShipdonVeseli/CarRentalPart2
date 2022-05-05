package com.example.carrentalcars.service;

import com.example.carrentalcars.config.RabbitMQConfig;
import com.example.carrentalcars.entity.Car;

import com.example.carrentalcars.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarService {
    private CarRepository carRepository;
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    public CarService(CarRepository carRepository, RabbitTemplate rabbitTemplate) {
        this.carRepository = carRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.RPC_QUEUE1)
    public void process(Message msg) {
        logger.info("server receive : {}",msg.toString());
        Message response = MessageBuilder.withBody(("i'm receive:"+new String(msg.getBody())).getBytes()).build();
        CorrelationData correlationData = new CorrelationData(msg.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(RabbitMQConfig.RPC_EXCHANGE, RabbitMQConfig.RPC_QUEUE2, response, correlationData);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(String message) {
        logger.info("Received with userid: {}", message);
        String[] parts = message.split(",");
        if(parts[0].equals("add")){
            Car updatedcar = carRepository.findById(parts[2]);
            if(updatedcar.getUserid().equals("0")) {
                updatedcar.setUserid(parts[1]);
                carRepository.save(updatedcar);
            }
        }else if(parts[0].equals("remove")){
            Car updatedcar = carRepository.findById(parts[2]);
            updatedcar.setUserid("0");
            carRepository.save(updatedcar);
        }
    }

    public Car createNewCar(Car car) {
        car.setId(UUID.randomUUID().toString());
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public Car getCar(String id) { return carRepository.findById(id); }

    public List<Car> getCarsByUserId(String id) { return carRepository.findByUserid(id); }

}
