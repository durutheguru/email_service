package com.julianduru.email.emailservice.repository;


import com.julianduru.email.emailservice.entity.EmailRequest;
import com.julianduru.email.emailservice.entity.EmailStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * created by julian
 */
@Repository
public interface EmailRequestRepository extends ReactiveMongoRepository<EmailRequest, String> {


    Flux<EmailRequest> findAllBy(Pageable pageable);


    Flux<EmailRequest> findByReferenceIn(Collection<String> references);


    Flux<EmailRequest> findByEmailStatusIn(Collection<EmailStatus> statusList);


}
