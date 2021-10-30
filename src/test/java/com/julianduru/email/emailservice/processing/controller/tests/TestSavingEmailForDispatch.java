package com.julianduru.email.emailservice.processing.controller.tests;


import com.julianduru.email.emailservice.BaseTestTemplate;
import com.julianduru.email.emailservice.TestSetup;
import com.julianduru.email.emailservice.data.EmailDTODataProvider;
import com.julianduru.email.emailservice.entity.EmailRequest;
import com.julianduru.email.emailservice.processing.EmailRequestDTO;
import com.julianduru.email.emailservice.processing.controller.EmailRequestController;
import com.julianduru.email.emailservice.repository.EmailRequestRepository;
import com.julianduru.email.emailservice.util.JSONUtil;
import com.julianduru.queues.api.dto.EmailDTO;
import lombok.Builder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by julian
 */
@Builder
public class TestSavingEmailForDispatch extends BaseTestTemplate<EmailRequestDTO> {


    private MockMvc mockMvc;


    private EmailDTODataProvider dataProvider;


    private EmailRequestRepository emailRequestRepository;


    @Override
    public TestSetup<EmailRequestDTO> setup() throws Throwable {
        var emailRequests = dataProvider.provide(3);
        var requestDTO = new EmailRequestDTO(emailRequests);

        return new TestSetup<>(requestDTO);
    }


    @Override
    public void perform(TestSetup<EmailRequestDTO> setup) throws Throwable {
        mockMvc.perform(
            post(EmailRequestController.PATH)
                .content(JSONUtil.asJsonString(setup.getData(), ""))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(status().is2xxSuccessful());
    }


    @Override
    public void verify(TestSetup<EmailRequestDTO> setup) throws Throwable {
        Flux<EmailRequest> requestFlux = emailRequestRepository.findByReferenceIn(
            setup.getData().getEmails()
                .stream()
                .map(EmailDTO::getReference)
                .collect(Collectors.toList())
        );

        List<EmailRequest> requestList = requestFlux.toStream().collect(Collectors.toList());

        assertThat(requestList)
            .hasSize(setup.getData().getEmails().size())
            .allSatisfy(r -> {
                assertThat(r.getReference()).isNotBlank();
                assertThat(r.getId()).isNotBlank();
            });
    }


}
