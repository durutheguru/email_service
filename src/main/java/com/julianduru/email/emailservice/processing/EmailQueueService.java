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


    private final QueueConfig queueConfig;


    private final EmailRequestService requestService;


    @Override
    public void configure() throws Exception {
        from("jms:queue:EMAIL_DISPATCH")
            .log("Fetched Requests: ${body}")
            .bean(requestService, "saveEmailRequests");
    }


}


