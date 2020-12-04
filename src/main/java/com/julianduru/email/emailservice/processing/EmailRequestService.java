package com.julianduru.email.emailservice.processing;


import com.julianduru.email.emailservice.processing.exception.EmailException;
import com.julianduru.omarze.api.dto.EmailDTO;
import com.sendgrid.Response;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by julian
 */
public interface EmailRequestService {


    default Map<String, Integer> sendEmails(Collection<EmailDTO> requests) {
        return requests
            .stream()
            .collect(
                Collectors.toMap(
                    EmailDTO::getReference,
                    e -> this.sendEmail(e).getStatusCode()
                )
            );
    }


    Response sendEmail(EmailDTO request);


    Flux<EmailDTO> fetchEmails(int page, int size);


    void saveEmailRequests(Collection<EmailDTO> requests) throws EmailException;


    void processEmailsPendingDispatch() throws EmailException;


}
