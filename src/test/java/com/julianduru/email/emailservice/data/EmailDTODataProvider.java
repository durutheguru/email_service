package com.julianduru.email.emailservice.data;


import com.github.javafaker.Faker;
import com.julianduru.email.emailservice.entity.EmailType;
import com.julianduru.email.emailservice.util.NullAwareBeanUtils;
import com.julianduru.omarze.api.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * created by julian
 */
@Component
@RequiredArgsConstructor
public class EmailDTODataProvider implements DataProvider<EmailDTO> {


    private Faker faker = new Faker();


    @Override
    public EmailDTO provide() {
        EmailDTO email = new EmailDTO();

        email.setReference(faker.code().isbn10());
        email.setSender(faker.internet().emailAddress());
        email.setRecipients(String.join(", ", faker.internet().emailAddress()));
        email.setTitle(faker.name().fullName() + " Email");
        email.setEmailMimeType(EmailType.TEXT.getMime());
        email.setMessage(faker.lorem().paragraph(4));

        return email;
    }


    @Override
    public EmailDTO provide(EmailDTO sample) {
        EmailDTO email = provide();

        NullAwareBeanUtils.copy(sample, email);

        return email;
    }


}
