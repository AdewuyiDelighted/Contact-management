package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactManagement;
import org.example.data.repositories.ContactRepository;
import org.example.dtos.request.*;
import org.example.exceptions.ContactDoesntExistException;
import org.example.exceptions.ContactNameExistException;
import org.example.exceptions.IncorrectEmailFormat;
import org.example.exceptions.IncorrectPhoneNumberFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.utils.Map.mapper;
import static org.example.utils.Verification.*;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactManagementService contactManagementService;
    @Autowired
    ContactRepository contactRepository;


    @Override
    public void addContact(AddContactRequest addContactRequest, Long contactManagementId) {
        checkUserInfo(addContactRequest);
        Contact contact = mapper(addContactRequest, contactManagementId);
        contactRepository.save(contact);
    }

    @Override
    public Contact editContact(EditContactRequest editContactRequest, Long contactManagementId) {
        validateEditContactInfo(editContactRequest, contactManagementId);
        for (Contact contact : findAllContactBelongingToUser(contactManagementId)) {
            if (contact.getPhoneNumber().equals(editContactRequest.getOldContact())) {
                contact.setPhoneNumber(editContactRequest.getNewContact());
                contactRepository.save(contact);
                return contact;
            }
        }
        return null;
    }

    private void validateEditContactInfo(EditContactRequest editContactRequest, Long contactManagementId) {
        if (!phoneNumberExist(editContactRequest.getOldContact(), contactManagementId))
            throw new ContactDoesntExistException("Contact doesnt exist");
        if (!validatePhoneNumber(editContactRequest.getNewContact()))
            throw new IncorrectPhoneNumberFormat("Wrong Phone number format");
    }

    @Override
    public Contact findAContact(String surname, String firstname, Long contactManagementId) {
        for (Contact contact : findAllContactBelongingToUser(contactManagementId)) {
            if (contact.getFirstname().equals(firstname) && contact.getSurname().equals(surname))
                return contact;
        }
        throw new ContactDoesntExistException("Contact doesnt not exist");
    }


    @Override
    public List<Contact> findAllContactBelongingToUser(Long contactManagementId) {
        List<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactRepository.findAll()) {
            if (contact.getContactManagementId().equals(contactManagementId)) contacts.add(contact);

        }
        return contacts;
    }

    @Override
    public List<Contact> findAllContactACategory(String categoryName, String email, Long contactManagementId) {
        List<Contact> contacts = new ArrayList<>();
        for (Contact contact : findAllContactBelongingToUser(contactManagementId)) {
            if (contact.getCategoryName().equals(categoryName)) contacts.add(contact);
        }
        return contacts;
    }

    @Override
    public Contact editContactInfo(EditContactInfoRequest editContactInfoRequest, Long contactManagementId) {
        Contact contact = findAContact(editContactInfoRequest.getFormerSurname(), editContactInfoRequest.getFormerFirstName(), contactManagementId);
        if (editContactInfoRequest.getNewSurname() != null) contact.setSurname(editContactInfoRequest.getNewSurname());
        if (editContactInfoRequest.getNewFirstName() != null)
            contact.setFirstname(editContactInfoRequest.getFormerFirstName());
        if (editContactInfoRequest.getAddress() != null) contact.setAddress(editContactInfoRequest.getAddress());
        if (editContactInfoRequest.getEmail() != null) contact.setEmail(editContactInfoRequest.getEmail());
        contactRepository.save(contact);
        return contact;
    }

    @Override
    public void deleteAContact(DeleteAContactRequest deleteAContactRequest, Long contactManagementId) {
        Contact contact = findAContact(deleteAContactRequest.getSurname(), deleteAContactRequest.getFirstName(), contactManagementId);
        contactRepository.deleteById(contact.getContactId());
    }

    @Override
    public void deleteAllContactInCategory(DeleteAllContactsInACategory deleteAllContactsInACategory, Long contactManagementId) {
        List<Contact> contacts = findAllContactACategory(deleteAllContactsInACategory.getCategoryName(), deleteAllContactsInACategory.getEmail(), contactManagementId);
        contactRepository.deleteAll(contacts);

    }

    @Override
    public void deleteAllContact(Long contactManagementId) {
        List<Contact> contacts = findAllContactBelongingToUser(contactManagementId);
        contactRepository.deleteAll(contacts);

    }

    @Override
    public Contact blockContact(BlockContactRequest blockContactRequest, Long contactManagementId) {
        Contact contact = findAContact(blockContactRequest.getSurname(), blockContactRequest.getFirstname(), contactManagementId);
        contact.setBlocked(true);
        return contact;
    }

    @Override
    public Contact unblockContact(UnblockContactRequest unblockContactRequest, Long contactManagementId) {
        Contact contact = findAContact(unblockContactRequest.getSurname(), unblockContactRequest.getFirstname(), contactManagementId);
        contact.setBlocked(false);
        return contact;

    }


    private void checkUserInfo(AddContactRequest addContactRequest) {
        if (contactNameExist(addContactRequest)) throw new ContactNameExistException("Contact name exist already");
        if (!validatePhoneNumber(addContactRequest.getPhoneNumber()))
            throw new IncorrectPhoneNumberFormat("Wrong Phone number format");
        if (!validateEmail(addContactRequest.getEmail())) throw new IncorrectEmailFormat("Wrong email format");
    }

    private boolean contactNameExist(AddContactRequest addContactRequest) {
        for (Contact contact : contactRepository.findAll()) {
            if (contact.getFirstname().equals(addContactRequest.getFirstName()) && contact.getSurname().equals(addContactRequest.getSurname()))
                return true;
        }
        return false;

    }

    private boolean phoneNumberExist(String oldContact, Long contactManagementId) {
        for (Contact contact : findAllContactBelongingToUser(contactManagementId)) {
            if (contact.getPhoneNumber().equals(oldContact)) return true;
        }
        throw new ContactDoesntExistException("Contact doesnt not exist");
    }


}
