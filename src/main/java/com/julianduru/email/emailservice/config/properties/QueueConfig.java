package com.julianduru.email.emailservice.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * created by julian
 */
@Component
@Data
@Validated
@ConfigurationProperties(prefix = "email.queue")
public class QueueConfig {


    private Long readTimeout = 5000L;


}
