package com.julianduru.email.emailservice.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "email.config.rabbit")
public class RabbitMQueueConfig {


    @NotEmpty(message = "Rabbit Host is required")
    private String host;


    @NotNull(message = "Rabbit Port is required")
    private Integer port;


    @NotEmpty(message = "Rabbit Username is required")
    private String username;


    @NotEmpty(message = "Rabbit Password is required")
    private String password;


}


