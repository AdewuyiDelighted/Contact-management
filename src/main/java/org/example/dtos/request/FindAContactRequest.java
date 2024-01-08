package org.example.dtos.request;

import lombok.Data;

@Data
public class FindAContactRequest {
    private String userEmail;
    private String surname;
    private String firstName;
}
