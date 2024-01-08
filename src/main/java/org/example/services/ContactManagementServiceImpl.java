package org.example.services;

import org.example.data.model.Contact;
import org.example.data.model.ContactManagement;
import org.example.data.repositories.ContactManagementRepository;
import org.example.dtos.request.*;
import org.example.exceptions.AppUnlockedException;
import org.example.exceptions.InvalidDetailsException;
import org.example.exceptions.UserExistException;
import org.example.utils.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.example.utils.Map.mapper;
import static org.example.utils.Verification.passwordChecker;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {
    @Autowired
    private ContactManagementRepository contactManagementRepository;
    @Autowired
    private ContactService contactService;


    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getSurname(), registerRequest.getFirstname()) != null)
            throw new UserExistException(fullName(registerRequest) + " already exist");
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
    public List<Contact> viewAllContactBelongToUser(FindAllContactRequest findAllContactRequest) {
        appUnlocked(findAllContactRequest.getEmail());
        ContactManagement contactManagement = userExist(findAllContactRequest.getSurname(), findAllContactRequest.getFirstName());
        if (contactManagement != null) return contactService.findAllContactBelongingToUser(contactManagement.getId());
        return null;
    }

    @Override
    public Contact viewAContact(FindAContactRequest findAContactRequest) {
        appUnlocked(findAContactRequest.getUserEmail());
        Optional<ContactManagement> contactManagement = findByEmail(findAContactRequest.getUserEmail());
        return contactService.findAContact(findAContactRequest.getSurname(), findAContactRequest.getFirstName(), contactManagement.get().getId());
    }

    @Override
    public List<Contact> viewAllContactBelongInCategory(FindAllContactsInACategory findAllContactsInACategory) {
        Optional<ContactManagement> contactManagement = findByEmail(findAllContactsInACategory.getEmail());
        if (contactManagement.isPresent())
            return contactService.findAllContactACategory(findAllContactsInACategory, contactManagement.get().getId());
        return null;
    }

    @Override
    public Contact editContactInfo(EditContactInfoRequest editContactInfoRequest) {
        return null;
    }

    @Override
    public ContactManagement findByUsername(String username) {
        return null;
    }

    @Override
    public Optional<ContactManagement> findByEmail(String email) {
        return contactManagementRepository.findByEmail(email);
    }


    private void appUnlocked(String email) {
        Optional<ContactManagement> contactManagement = contactManagementRepository.findByEmail(email);
        if (contactManagement.get().isLocked()) throw new AppUnlockedException("App is locked");
    }

    private void validateEmailAndPassword(LoginRequest loginRequest, ContactManagement contactManagement) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!loginRequest.getEmail().equals(contactManagement.getEmail()))
            throw new InvalidDetailsException("Details incorrect");
        if (!passwordEncoder.matches(loginRequest.getPassword(), contactManagement.getPassword()))
            throw new InvalidDetailsException("Details incorrect");
    }

    private ContactManagement userExist(String surname, String firstname) {
        for (ContactManagement contactManagement : contactManagementRepository.findAll()) {
            if (contactManagement.getFirstName().equals(firstname) && contactManagement.getSurname().equals(surname))
                return contactManagement;
        }
        return null;
    }

    private String fullName(RegisterRequest registerRequest) {
        return registerRequest.getSurname() + " " + registerRequest.getFirstname();
    }


}
