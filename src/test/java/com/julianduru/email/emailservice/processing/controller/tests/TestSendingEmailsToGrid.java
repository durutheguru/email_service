package com.julianduru.email.emailservice.processing.controller.tests;


import com.julianduru.email.emailservice.BaseTestTemplate;
import com.julianduru.email.emailservice.TestSetup;
import com.julianduru.email.emailservice.data.EmailDTODataProvider;
import com.julianduru.email.emailservice.processing.EmailRequestDTO;
import com.julianduru.email.emailservice.processing.controller.EmailRequestController;
import com.julianduru.email.emailservice.util.JSONUtil;
import com.julianduru.queues.api.dto.EmailDTO;
import lombok.Builder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by julian
 */
@Builder
public class TestSendingEmailsToGrid extends BaseTestTemplate<EmailRequestDTO> {


    private MockMvc mockMvc;


    private EmailDTODataProvider dataProvider;


    @Override
    public TestSetup<EmailRequestDTO> setup() {
        var sample = new EmailDTO();
        sample.setSender("durutheguru@gmail.com");
        sample.setRecipients("julian.duru@yahoo.com");

        var emailRequest = dataProvider.provide(sample);
        var requestDTO = new EmailRequestDTO(List.of(emailRequest));

        return new TestSetup<>(requestDTO);
    }


    @Override
    public void perform(TestSetup<EmailRequestDTO> setup) throws Throwable {
        mockMvc.perform(
            post(EmailRequestController.PATH + "/send")
            .content(JSONUtil.asJsonString(setup.getData(), ""))
            .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(status().is2xxSuccessful());
    }



}
