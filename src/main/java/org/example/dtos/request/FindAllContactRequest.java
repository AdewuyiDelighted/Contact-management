package org.example.dtos.request;

import lombok.Data;

@Data
public class FindAllContactRequest {
    private String surname;
    private String firstName;
    private String email;

}
