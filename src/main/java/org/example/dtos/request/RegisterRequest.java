package org.example.dtos.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstname;
    private String surname;
    private String email;
    private String phoneNumber;
    private  String address;
    private String password;
}
