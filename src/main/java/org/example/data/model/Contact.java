package org.example.data.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;
    private Long contactManagementId;
    private String surname;
    private String firstname;
    private String categoryName;
    private String address;
    private String email;
    private String phoneNumber;
    private String userEmail;
    private boolean isBlocked;



}
