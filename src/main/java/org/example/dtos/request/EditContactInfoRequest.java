package org.example.dtos.request;

import lombok.Data;

@Data
public class EditContactInfoRequest {
    private String formerFirstName;
    private String formerSurname;
    private String newFirstName;
    private String newSurname;
    private String phoneNumber;
    private String email;
    private String userEmail;
    private String categoryName;
    private String address;
}
