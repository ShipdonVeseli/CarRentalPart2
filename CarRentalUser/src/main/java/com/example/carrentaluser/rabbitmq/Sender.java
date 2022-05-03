package com.example.carrentaluser.rabbitmq;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
public class Sender {

    public void sendHalloWorld(){
        try {
            String queueName="test queue";
            String host = "localhost";
            String message ="hallo world";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);

            Connection connection= factory.newConnection();
            Channel channel=connection.createChannel();

            channel.queueDeclare(queueName,false,false,false,null );
            channel.basicPublish("",queueName,null,message.getBytes());


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
