package com.julianduru.email.emailservice.processing.controller.tests;


import com.julianduru.email.emailservice.BaseTestTemplate;
import com.julianduru.email.emailservice.TestSetup;
import com.julianduru.email.emailservice.data.EmailDTODataProvider;
import com.julianduru.email.emailservice.processing.EmailRequestDTO;
import com.julianduru.email.emailservice.processing.controller.EmailRequestController;
import com.julianduru.email.emailservice.util.JSONUtil;
import lombok.Builder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by julian
 */
@Builder
public class TestSendingTooManyEmailsToGrid extends BaseTestTemplate<EmailRequestDTO> {


    private MockMvc mockMvc;


    private EmailDTODataProvider dataProvider;


    @Override
    public TestSetup<EmailRequestDTO> setup() {
        var emailRequests = dataProvider.provide(4);
        var requestDTO = new EmailRequestDTO(emailRequests);

        return new TestSetup<>(requestDTO);
    }


    @Override
    public void perform(TestSetup<EmailRequestDTO> setup) throws Throwable {
        mockMvc.perform(
            post(EmailRequestController.PATH + "/send")
                .content(JSONUtil.asJsonString(setup.getData(), ""))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(status().is4xxClientError());
    }


}
