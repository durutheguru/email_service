package com.julianduru.email.emailservice.processing;


import com.julianduru.data.email.api.dto.EmailDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * created by julian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDTO {


    @Valid
    @NotEmpty(message = "Emails cannot be empty")
    @Size(max = 3, message = "Dispatch API does not support above 3 emails")
    private Collection<EmailDTO> emails;


}
