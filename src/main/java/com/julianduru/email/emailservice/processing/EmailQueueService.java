package com.julianduru.email.emailservice.processing;


import com.julianduru.email.emailservice.config.properties.QueueConfig;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * created by julian
 */
@Component
@RequiredArgsConstructor
public class EmailQueueService extends RouteBuilder {


    private final EmailRequestService requestService;


    @Override
    public void configure() throws Exception {
        from("rabbitmq:SOCIALOTTO?routingKey=email_dispatch")
            .log("Fetched Requests: ${body}")
            .bean(requestService, "saveEmailRequests");
    }


}


