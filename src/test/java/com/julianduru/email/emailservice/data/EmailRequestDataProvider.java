package com.julianduru.email.emailservice.data;


import com.github.javafaker.Faker;
import com.julianduru.email.emailservice.entity.EmailRequest;
import com.julianduru.email.emailservice.entity.EmailType;
import com.julianduru.email.emailservice.util.NullAwareBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * created by julian
 */
@Component
@RequiredArgsConstructor
public class EmailRequestDataProvider implements DataProvider<EmailRequest> {


    private Faker faker = new Faker();


    @Override
    public EmailRequest provide() {
        EmailRequest request = new EmailRequest();

        request.setReference(faker.code().isbn10());
        request.setSender(faker.internet().emailAddress());
        request.setRecipients(List.of(faker.internet().emailAddress()));
        request.setTitle(faker.name().fullName() + " Email");
        request.setEmailType(EmailType.TEXT);
        request.setMessage(faker.lorem().paragraph(4));

        return request;
    }


    @Override
    public EmailRequest provide(EmailRequest sample) {
        EmailRequest request = provide();

        NullAwareBeanUtils.copy(sample, request);

        return request;
    }


}
