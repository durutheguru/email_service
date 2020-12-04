package com.julianduru.email.emailservice.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by julian
 */
@Getter
@RequiredArgsConstructor
public enum EmailType {


    TEXT("text/plain"),

    HTML("text/html");


    private final String mime;


    public static EmailType ofMime(String mime) {
        for (EmailType type : values()) {
            if (type.getMime().equalsIgnoreCase(mime)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mime Type argument is not recognized");
    }


}

