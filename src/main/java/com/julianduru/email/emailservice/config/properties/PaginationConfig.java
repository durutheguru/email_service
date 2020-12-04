package com.julianduru.email.emailservice.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * created by julian
 */
@Data
@Component
@ConfigurationProperties(prefix = "email.request.page")
public class PaginationConfig {


    private Integer size = 100;


}
