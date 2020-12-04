package com.julianduru.email.emailservice.processing.impl;


import com.julianduru.email.emailservice.entity.EmailRequest;
import com.julianduru.email.emailservice.processing.EmailSender;
import com.julianduru.email.emailservice.processing.exception.EmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * created by julian
 */
@Slf4j
@Component
public class EmailSenderImpl implements EmailSender {


    @Value("${email.sendgrid.api-key}")
    private String sendgridApiKey;


    @Override
    public Response sendEmailRequest(EmailRequest request) {
        var mail = request.toMail();
        var sg = new SendGrid(sendgridApiKey);

        return processMailToGrid(mail, sg);
    }


    private Response processMailToGrid(Mail mail, SendGrid sendGrid) {
        try {
            var apiRequest = new Request();

            apiRequest.setMethod(Method.POST);
            apiRequest.setEndpoint("mail/send");
            apiRequest.setBody(mail.build());

            var response = sendGrid.api(apiRequest);

            log.info(
                "SendGrid Response. Status Code: {}, Body: {}",
                response.getStatusCode(), response.getBody()
            );

            return response;
        }
        catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new EmailException(ex);
        }
    }


}
