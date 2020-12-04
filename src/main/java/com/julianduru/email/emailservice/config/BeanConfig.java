package com.julianduru.email.emailservice.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by julian
 */
@Configuration
public class BeanConfig {


    @Value("${email.config.default-thread-pool-size:50}")
    private Integer defaultThreadPoolSize;


    @Bean
    @Qualifier("defaultThreadPoolExecutor")
    public ExecutorService defaultThreadPoolExecutor() {
        return Executors.newFixedThreadPool(defaultThreadPoolSize);
    }


}
