package org.example.utils;

import org.example.data.model.Contact;
import org.example.data.model.ContactManagement;
import org.example.dtos.request.AddContactRequest;
import org.example.dtos.request.RegisterRequest;


public class Map {
    public static ContactManagement mapper(RegisterRequest registerRequest) {
        ContactManagement contactManagement = new ContactManagement();
        contactManagement.setFirstName(registerRequest.getFirstname());
        contactManagement.setSurname(registerRequest.getSurname());
        contactManagement.setEmail(registerRequest.getEmail());
        contactManagement.setPassword(registerRequest.getPassword());
        contactManagement.setPhoneNumber(registerRequest.getPhoneNumber());
        return contactManagement;
    }

    private String fullName(RegisterRequest registerRequest) {
        return registerRequest.getSurname() + " " + registerRequest.getFirstname();
    }

    public static Contact mapper(AddContactRequest addContactRequest,Long contactManagementId) {
        Contact contact = new Contact();
        contact.setFirstname(addContactRequest.getFirstName());
        contact.setSurname(addContactRequest.getSurname());
        contact.setPhoneNumber(addContactRequest.getPhoneNumber());
        contact.setEmail(addContactRequest.getEmail());
        contact.setUserEmail(addContactRequest.getUserEmail());
        contact.setCategoryName(addContactRequest.getCategoryName());
        contact.setContactManagementId(contactManagementId);
        return contact;

    }
}
