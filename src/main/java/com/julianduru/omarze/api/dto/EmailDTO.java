package com.julianduru.omarze.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class EmailDTO implements Serializable {
    

    private static final long serialVersionUID = 6578895403089712888L;


    @NotEmpty(message = "Email Reference must be provided")
    private String reference;


    @NotEmpty(message = "Email Sender is required")
    private String sender;


    @NotEmpty(message = "Email Title is required")
    private String title;


    @NotEmpty(message = "Email Recipients are required")
    private String recipients;


    private String ccs;


    @NotEmpty(message = "Email Message cannot be empty")
    private String message;


    private String emailMimeType;
    

}
