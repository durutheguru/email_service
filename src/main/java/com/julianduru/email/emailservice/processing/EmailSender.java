package com.julianduru.email.emailservice.processing;


import com.sendgrid.Response;
import com.julianduru.email.emailservice.entity.EmailRequest;


/**
 * created by julian
 */
public interface EmailSender {


    Response sendEmailRequest(EmailRequest request);


}
