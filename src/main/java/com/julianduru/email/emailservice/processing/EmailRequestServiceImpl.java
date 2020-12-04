package com.julianduru.email.emailservice.processing;


import com.julianduru.email.emailservice.entity.EmailRequest;
import com.julianduru.email.emailservice.entity.EmailStatus;
import com.julianduru.email.emailservice.processing.exception.EmailException;
import com.julianduru.email.emailservice.repository.EmailRequestRepository;
import com.julianduru.omarze.api.dto.EmailDTO;
import com.sendgrid.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * created by julian
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailRequestServiceImpl implements EmailRequestService {


    private final EmailSender emailSender;


    private final EmailRequestRepository requestRepository;


    private final ExecutorService defaultThreadPoolExecutor;



    @Override
    public Response sendEmail(EmailDTO request) {
        return emailSender.sendEmailRequest(EmailRequest.fromEmailDTO(request));
    }


    @Override
    public Flux<EmailDTO> fetchEmails(int page, int size) {
        return requestRepository
            .findAllBy(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate")))
            .map(EmailRequest::toEmailDTO);
    }


    @Override
    public void saveEmailRequests(Collection<EmailDTO> requests) throws EmailException {
        requestRepository
            .saveAll(
                requests
                    .stream()
                    .map(EmailRequest::fromEmailDTO)
                    .collect(Collectors.toList())
            )
            .subscribe(
                r -> log.info("Saved Email Request {}", r.getReference())
            );
    }


    @Override
    public void processEmailsPendingDispatch() throws EmailException {
        requestRepository
            .findByEmailStatusIn(List.of(EmailStatus.PENDING_DISPATCH))
            .subscribeOn(Schedulers.fromExecutor(defaultThreadPoolExecutor))
            .map(emailRequest -> {
                emailRequest.setInProgress();
                requestRepository.save(emailRequest);

                return Map.entry(emailRequest, this.sendEmail(emailRequest.toEmailDTO()));
            })
            .subscribe(
                entry -> {
                    var request = entry.getKey();
                    var response = entry.getValue();

                    request.updateFromResponse(response);
                    requestRepository.save(request).block();
                }
            );
    }


}

