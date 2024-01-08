package org.example.dtos.request;

import lombok.Data;

@Data
public class EditContactInfoRequest {
    private String firstName;
    private String surname;
    private String phoneNumber;
    private String email;
    private String userEmail;
    private String categoryName;
    private String address;
}
