package com.julianduru.email.emailservice.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;

/**
 * created by julian
 */
@TestConfiguration
@EnableAutoConfiguration(
    exclude = {
        JmxAutoConfiguration.class,
    }
)
public class TestConfig {

}
