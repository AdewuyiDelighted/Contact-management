package org.example.dtos.request;

import lombok.Data;

@Data
public class EditUserInfoRequest {
    private String formerFirstname;
    private String newFirstname;
    private String formerSurname;
    private String newSurname;
    private String formerEmail;
    private String newEmail;
    private String formerPhoneNumber;
    private String newPhoneNumber;
    private String formerAddress;
    private String newAddress;
}
