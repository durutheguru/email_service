package com.julianduru.email.emailservice.processing.controller;


import com.julianduru.email.emailservice.config.TestConfig;
import com.julianduru.email.emailservice.config.TestDataSourceConfig;
import com.julianduru.email.emailservice.data.EmailDTODataProvider;
import com.julianduru.email.emailservice.processing.controller.tests.TestSavingEmailForDispatch;
import com.julianduru.email.emailservice.processing.controller.tests.TestSendingEmailsToGrid;
import com.julianduru.email.emailservice.processing.controller.tests.TestSendingTooManyEmailsToGrid;
import com.julianduru.email.emailservice.repository.EmailRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;


/**
 * created by julian
 */
@Slf4j
//@Testcontainers
@ExtendWith({SpringExtension.class})
@SpringBootTest(
    classes = {
        TestConfig.class,
        TestDataSourceConfig.class
    }
)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {
    MongoAutoConfiguration.class,
    MongoDataAutoConfiguration.class}
)
@EnableReactiveMongoRepositories
public class EmailRequestControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private EmailDTODataProvider dataProvider;


    @Autowired
    private EmailRequestRepository emailRequestRepository;


    @Test
    public void testSavingEmailsForDispatch() throws Throwable {
        TestSavingEmailForDispatch.builder()
            .mockMvc(mockMvc)
            .dataProvider(dataProvider)
            .emailRequestRepository(emailRequestRepository)
            .build()
            .test();
    }


    @Test
    public void testSendingTooManyEmailsToGrid() throws Throwable {
        TestSendingTooManyEmailsToGrid.builder()
            .mockMvc(mockMvc)
            .dataProvider(dataProvider)
            .build()
            .test();
    }


    @Test
    public void testSendingEmailsToGrid() throws Throwable {
        TestSendingEmailsToGrid.builder()
            .mockMvc(mockMvc)
            .dataProvider(dataProvider)
            .build()
            .test();
    }


}


