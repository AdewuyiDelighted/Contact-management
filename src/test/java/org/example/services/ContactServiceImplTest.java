package org.example.services;

import org.example.data.model.ContactManagement;
import org.example.data.repositories.ContactRepository;
import org.example.dtos.request.AddContactRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactServiceImplTest {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactManagementService contactManagementService;
    @Autowired
    ContactService contactService;

    @BeforeEach public void startAllWith(){
        contactRepository.deleteAll();
    }
}