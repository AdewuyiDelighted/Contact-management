package org.example.dtos.request;

import lombok.Data;

@Data
public class DeleteAContactRequest {
    private String userEmail;
    private String surname;
    private String firstName;
}
