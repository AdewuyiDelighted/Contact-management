package org.example.services;

import org.example.data.model.Contact;
import org.example.dtos.request.*;

import java.util.List;

public interface ContactService {
    void addContact(AddContactRequest addContactRequest, Long contactManagementId);

    Contact editContact(EditContactRequest editContactRequest, Long contactManagementId);


    List<Contact> findAllContactBelongingToUser(Long contactListId);

    List<Contact> findAllContactACategory(String categoryName, String email, Long contactManagementId);

    Contact editContactInfo(EditContactInfoRequest editContactInfoRequest, Long contactManagementId);

    void deleteAContact(DeleteAContactRequest deleteAContactRequest, Long contactManagementId);

    void deleteAllContactInCategory(DeleteAllContactsInACategory deleteAllContactsInACategory, Long contactManagementId);

    void deleteAllContact(Long contactManagementId);
    Contact blockContact(BlockContactRequest blockContactRequest,Long contactManagementId);
    Contact unblockContact(UnblockContactRequest unblockContactRequest,Long contactManagementId);
    Contact findAContact(String surname, String firstName, Long contactManagementId);

}
