package org.example.services;

import org.example.data.model.Contact;
import org.example.dtos.request.*;

import java.util.List;

public interface ContactService {
    void addContact(AddContactRequest addContactRequest, Long contactManagementId);

    Contact editContact(EditContactRequest editContactRequest, Long contactManagementId);

    Contact findAContact(String surname, String firstname, Long contactManagementId);

    List<Contact> findAllContactBelongingToUser(Long contactListId);

    List<Contact> findAllContactACategory(FindAllContactsInACategory findAllContactsInACategory, Long contactManagementId);


}
