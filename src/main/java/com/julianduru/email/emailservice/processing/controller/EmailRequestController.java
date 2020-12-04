package com.julianduru.email.emailservice.processing.controller;


import com.julianduru.email.emailservice.Constants;
import com.julianduru.email.emailservice.processing.EmailRequestDTO;
import com.julianduru.email.emailservice.processing.EmailRequestService;
import com.julianduru.omarze.api.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * created by julian
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = EmailRequestController.PATH)
public class EmailRequestController {


    public static final String PATH = Constants.API_VERSION + "/email";


    private final EmailRequestService requestService;



    @PostMapping
    public void saveEmailRequests(@Valid @RequestBody EmailRequestDTO emailRequestDTO) {
        requestService.saveEmailRequests(emailRequestDTO.getEmails());
    }


    @GetMapping
    public Flux<EmailDTO> fetchEmailRequests(
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return requestService.fetchEmails(page, size);
    }


    @PostMapping("/send")
    public Map<String, Integer> sendEmailRequests(@Valid @RequestBody EmailRequestDTO emailRequestDTO) {
        return requestService.sendEmails(emailRequestDTO.getEmails());
    }


}
