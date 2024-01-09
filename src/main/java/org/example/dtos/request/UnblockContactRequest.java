package org.example.dtos.request;

import lombok.Data;

@Data
public class UnblockContactRequest {
    private String surname;
    private String firstname;
    private String email;
}
