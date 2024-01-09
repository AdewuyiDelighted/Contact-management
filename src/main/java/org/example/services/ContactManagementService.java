package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactManagement;
import org.example.dtos.request.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ContactManagementService {
    void register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    Optional<ContactManagement> findByEmail(String email);

    void addContact(AddContactRequest addContactRequest);

    Contact editContact(EditContactRequest editContactRequest);

    List<Contact> viewAllContactBelongToUser(FindAllContactRequest findAllContactRequest);

    Contact viewAContact(FindAContactRequest findAContactRequest);

    List<Contact> viewAllContactBelongInCategory(FindAllContactsInACategory findAllContactsInACategory);

    Contact editContactInfo(EditContactInfoRequest editContactInfoRequest);

    void deleteAContact(DeleteAContactRequest deleteAContactRequest);

    void deleteAllContactBelongInCategory(DeleteAllContactsInACategory deleteAllContactsInACategory);

    void deleteAllContact(DeleteAllContactRequest deleteAllContactRequest);

    Contact blockContact(BlockContactRequest blockContactRequest);
    Contact unblockContact(UnblockContactRequest unblockContactRequest);
}
