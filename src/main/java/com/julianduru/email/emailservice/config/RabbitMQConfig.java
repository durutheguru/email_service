package com.julianduru.email.emailservice.config;


import com.julianduru.email.emailservice.config.properties.RabbitMQueueConfig;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Bean
    public ConnectionFactory rabbitConnectionFactory(RabbitMQueueConfig config) {
        var connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(config.getHost());
        connectionFactory.setPort(connectionFactory.getPort());
        connectionFactory.setUsername(connectionFactory.getUsername());
        connectionFactory.setPassword(connectionFactory.getPassword());

        return connectionFactory;
    }


}

