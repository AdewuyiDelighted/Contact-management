package org.example.dtos.request;

import lombok.Data;

@Data
public class EditContactRequest {
    private String userEmail;
    private String oldContact;
    private String newContact;
}
