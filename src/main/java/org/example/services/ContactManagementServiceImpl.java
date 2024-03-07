package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactManagement;
import org.example.data.repositories.ContactManagementRepository;
import org.example.dtos.request.*;
import org.example.exceptions.AppUnlockedException;
import org.example.exceptions.ContactDoesntExistException;
import org.example.exceptions.InvalidDetailsException;
import org.example.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.utils.Map.mapper;
import static org.example.utils.Verification.*;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {
    @Autowired
    private ContactManagementRepository contactManagementRepository;
    @Autowired
    private ContactService contactService;


    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getSurname(), registerRequest.getFirstname(), registerRequest.getEmail()) != null)
            throw new UserExistException(fullName(registerRequest) + " or " + registerRequest.getEmail() + " already exist");
        passwordChecker(registerRequest);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodedPassword);
        ContactManagement contactManagement = mapper(registerRequest);
        contactManagementRepository.save(contactManagement);

    }

    @Override
    public void login(LoginRequest loginRequest) {
        Optional<ContactManagement> contactManagement = contactManagementRepository.findByEmail(loginRequest.getEmail());
        if (contactManagement.isEmpty()) throw new InvalidDetailsException("Invalid details");
        validateEmailAndPassword(loginRequest, contactManagement.get());
        contactManagement.get().setLocked(false);
        contactManagementRepository.save(contactManagement.get());
    }


    @Override
    public void addContact(AddContactRequest addContactRequest) {
        appUnlocked(addContactRequest.getUserEmail());
        validatePhoneNumber(addContactRequest.getPhoneNumber());
        validateEmail(addContactRequest.getEmail());
        Optional<ContactManagement> contactManagement = contactManagementRepository.findByEmail(addContactRequest.getUserEmail());
        if (contactManagement.isPresent())
            contactService.addContact(addContactRequest, contactManagement.get().getId());
    }

    @Override
    public Contact editContact(EditContactRequest editContactRequest) {
        appUnlocked(editContactRequest.getUserEmail());
        Optional<ContactManagement> contactManagement = findByEmail(editContactRequest.getUserEmail());
        if (contactManagement.isPresent())
            return contactService.editContact(editContactRequest, contactManagement.get().getId());
        return null;

    }

    @Override
    public List<Contact> viewAllContactBelongToUser(String email) {
        List<Contact> userContacts = new ArrayList<>();
        appUnlocked(email);
        Optional<ContactManagement> contactManagement = findByEmail(email);
        if (contactManagement.isPresent()) {
            userContacts = contactService.findAllContactBelongingToUser(contactManagement.get().getId());
            if (userContacts != null) {
                return userContacts;
            } else throw new ContactDoesntExistException("No Contact Added");
        }
        throw new InvalidDetailsException("Invalid Details");
    }

    @Override
    public Contact viewAContact(FindAContactRequest findAContactRequest) {
        appUnlocked(findAContactRequest.getUserEmail());
        Optional<ContactManagement> contactManagement = findByEmail(findAContactRequest.getUserEmail());
        return contactService.findAContact(findAContactRequest.getSurname(), findAContactRequest.getFirstName(), contactManagement.get().getId());
    }

    @Override
    public List<Contact> viewAllContactBelongInCategory(FindAllContactsInACategory findAllContactsInACategory) {
        appUnlocked(findAllContactsInACategory.getEmail());
        Optional<ContactManagement> contactManagement = findByEmail(findAllContactsInACategory.getEmail());
        if (contactManagement.isPresent()) {
            return contactService.findAllContactACategory(findAllContactsInACategory.getCategoryName(), findAllContactsInACategory.getEmail(), contactManagement.get().getId());
        }
        throw new InvalidDetailsException("Invalid details");
    }

    @Override
    public Contact editContactInfo(EditContactInfoRequest editContactInfoRequest) {
        Optional<ContactManagement> contactManagement = findByEmail(editContactInfoRequest.getUserEmail());
        if (contactManagement.isPresent())
            return contactService.editContactInfo(editContactInfoRequest, contactManagement.get().getId());
        return null;
    }

    @Override
    public Contact blockContact(BlockContactRequest blockContactRequest) {
        appUnlocked(blockContactRequest.getEmail());
        Optional<ContactManagement> contactManagement = findByEmail(blockContactRequest.getEmail());
        return contactService.blockContact(blockContactRequest, contactManagement.get().getId());
    }

    @Override
    public Contact unblockContact(UnblockContactRequest unblockContactRequest) {
        appUnlocked(unblockContactRequest.getEmail());
        Optional<ContactManagement> contactManagement = findByEmail(unblockContactRequest.getEmail());
        return contactService.unblockContact(unblockContactRequest, contactManagement.get().getId());
    }

    @Override
    public ContactManagement editUserInfo(EditUserInfoRequest editUserInfoRequest) {
        appUnlocked(editUserInfoRequest.getFormerEmail());
        Optional<ContactManagement> contactManagement = findByEmail(editUserInfoRequest.getFormerEmail());
        if (contactManagement.isPresent()) {
            if (editUserInfoRequest.getNewFirstname() != null)
                contactManagement.get().setFirstName(editUserInfoRequest.getNewFirstname());
            if (editUserInfoRequest.getNewSurname() != null)
                contactManagement.get().setSurname(editUserInfoRequest.getNewSurname());
            if (editUserInfoRequest.getNewAddress() != null)
                contactManagement.get().setAddress(editUserInfoRequest.getNewAddress());
            if (editUserInfoRequest.getNewEmail() != null)
                contactManagement.get().setEmail(editUserInfoRequest.getNewEmail());
            if (editUserInfoRequest.getNewPhoneNumber() != null)
                contactManagement.get().setPhoneNumber(editUserInfoRequest.getNewPhoneNumber());
            contactManagementRepository.save(contactManagement.get());
            return contactManagement.get();

        }
        return null;
    }

    @Override
    public void deleteAccount(String email) {
        appUnlocked(email);
        Optional<ContactManagement> contactManagement = findByEmail(email);
        contactManagementRepository.delete(contactManagement.get());

    }


    @Override
    public void deleteAContact(DeleteAContactRequest deleteAContactRequest) {
        appUnlocked(deleteAContactRequest.getUserEmail());
        Optional<ContactManagement> contactManagement = findByEmail(deleteAContactRequest.getUserEmail());
        if (contactManagement.isPresent())
            contactService.deleteAContact(deleteAContactRequest, contactManagement.get().getId());


    }

    @Override
    public void deleteAllContactBelongInCategory(DeleteAllContactsInACategory deleteAllContactsInACategory) {
        appUnlocked(deleteAllContactsInACategory.getEmail());
        Optional<ContactManagement> contactManagement = findByEmail(deleteAllContactsInACategory.getEmail());
        if (contactManagement.isPresent())
            contactService.deleteAllContactInCategory(deleteAllContactsInACategory, contactManagement.get().getId());


    }

    @Override
    public void deleteAllContact(DeleteAllContactRequest deleteAllContactRequest) {
        appUnlocked(deleteAllContactRequest.getEmail());
        Optional<ContactManagement> contactManagement = findByEmail(deleteAllContactRequest.getEmail());
        contactService.deleteAllContact(contactManagement.get().getId());

    }


    @Override
    public Optional<ContactManagement> findByEmail(String email) {
        Optional<ContactManagement> contactManagement = contactManagementRepository.findByEmail(email);
        if (contactManagement.isPresent()) return contactManagement;
        else throw new InvalidDetailsException("Invalid Details");
    }


    private void appUnlocked(String email) {
        Optional<ContactManagement> contactManagement = contactManagementRepository.findByEmail(email);
        if (contactManagement.get().isLocked()) throw new AppUnlockedException("User need to login in");
    }

    private void validateEmailAndPassword(LoginRequest loginRequest, ContactManagement contactManagement) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!loginRequest.getEmail().equals(contactManagement.getEmail()))
            throw new InvalidDetailsException("Invalid details");
        if (!passwordEncoder.matches(loginRequest.getPassword(), contactManagement.getPassword()))
            throw new InvalidDetailsException("Invalid details");
    }

    private ContactManagement userExist(String surname, String firstname, String email) {
        for (ContactManagement contactManagement : contactManagementRepository.findAll()) {
            if (contactManagement.getFirstName().equals(firstname) && contactManagement.getSurname().equals(surname) && contactManagement.getEmail().equals(email))
                return contactManagement;
        }
        return null;
    }

    private String fullName(RegisterRequest registerRequest) {
        return registerRequest.getSurname() + " " + registerRequest.getFirstname();
    }


}
