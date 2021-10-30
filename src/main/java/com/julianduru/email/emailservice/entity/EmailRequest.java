package com.julianduru.email.emailservice.entity;


import com.julianduru.queues.api.dto.EmailDTO;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * created by julian
 */
@Data
@Document
public class EmailRequest {


    @Id
    private String id;


    @NotEmpty(message = "Reference cannot be empty")
    private String reference;


    @NotEmpty(message = "Sender should not be empty")
    private String sender;


    @NotEmpty(message = "Title should not be empty")
    private String title;


    @NotEmpty(message = "List of Recipients is required")
    private List<String> recipients;


    private List<String> ccs;


    @NotEmpty(message = "Message should not be empty")
    private String message;


    private EmailType emailType;


    private EmailStatus emailStatus;


    private String statusMessage;


    @CreatedDate
    private String createdDate;


    @LastModifiedDate
    private String lastModifiedDate;


//    @PrePersist
//    public void prePersist() {
//        timeAdded = ZonedDateTime.now();
//    }
//
//
//    @PreUpdate
//    public void preUpdate() {
//        timeUpdated = ZonedDateTime.now();
//    }



    public void setInProgress() {
        setStatusMessage("In Progress");
        setEmailStatus(EmailStatus.DISPATCH_IN_PROGRESS);
    }


    public void updateFromResponse(Response response) {
        if (response.getStatusCode() == 202) {
            setEmailStatus(EmailStatus.DISPATCH_SUCCESSFUL);
        }
        else {
            setEmailStatus(EmailStatus.DISPATCH_FAILED);
        }

        setStatusMessage(response.getBody());
    }


    private boolean hasCCs() {
        List<String> ccs;
        return (ccs = getCcs()) != null && !ccs.isEmpty();
    }


    @Override
    public String toString() {
        return String.format("{sender=%s,title=%s,message=%s,emailType=%s}",
            getSender(), getTitle(), getMessage(), getEmailType() == null ? "" : getEmailType().toString()
        );
    }


    public Mail toMail() {
        var from = new Email(getSender());
        var subject = getTitle();

        //TODO: move to support for multiple recipients
        var to = new Email(getRecipients().get(0));
        var content = new Content(getEmailType().getMime(), getMessage());

        return new Mail(from, subject, to, content);
    }


    public static EmailRequest fromEmailDTO(EmailDTO email) {
        var request = new EmailRequest();

        request.setTitle(email.getTitle());
        request.setSender(email.getSender());
        request.setMessage(email.getMessage());
        request.setRecipients(List.of(email.getRecipients().split("\\s*,\\s*")));
        request.setEmailType(EmailType.ofMime(email.getEmailMimeType()));
        request.setReference(email.getReference());
        request.setEmailStatus(EmailStatus.PENDING_DISPATCH);

        if (email.getCcs() != null && !email.getCcs().isBlank()) {
            request.setCcs(List.of(email.getCcs().split("\\s*,\\s*")));
        }

        return request;
    }


    public EmailDTO toEmailDTO() {
        var email = new EmailDTO();

        email.setReference(getReference());
        email.setTitle(getTitle());
        email.setSender(getSender());
        email.setMessage(getMessage());
        email.setEmailMimeType(getEmailType().getMime());
        email.setRecipients(String.join(", ", getRecipients()));

        if (hasCCs()) {
            email.setCcs(String.join(", ", getCcs()));
        }

        return email;
    }


}

