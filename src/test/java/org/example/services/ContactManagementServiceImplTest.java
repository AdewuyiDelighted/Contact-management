package org.example.services;

import org.example.data.model.ContactManagement;
import org.example.data.repositories.ContactManagementRepository;
import org.example.data.repositories.ContactRepository;
import org.example.dtos.request.*;
import org.example.exceptions.InvalidDetailsException;
import org.example.exceptions.PasswordTooWeakException;
import org.example.exceptions.UserExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ContactManagementServiceImplTest {
    @Autowired
    private ContactManagementService contactManagementService;
    @Autowired
    private ContactManagementRepository contactManagementRepository;
    @Autowired
    private ContactRepository contactRepository;

    @BeforeEach
    public void startWith() {
        contactManagementRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegisterContactManagement() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Delighted");
        registerRequest.setSurname("Adewuyi");
        registerRequest.setPassword("Adewuyi@123");
        registerRequest.setEmail("deborahdelighted5@gmail.com");
        registerRequest.setPhoneNumber("08072034442");
        contactManagementService.register(registerRequest);
        assertEquals(1, contactManagementRepository.count());
    }

    @Test
    public void testThatUserCantRegisterWithWeakPassword() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Delighted");
        registerRequest.setSurname("Adewuyi");
        registerRequest.setPassword("Adewuyi");
        registerRequest.setEmail("deborahdelighted5@gmail.com");
        registerRequest.setPhoneNumber("08072034442");
        assertThrows(PasswordTooWeakException.class, () -> contactManagementService.register(registerRequest));
    }

    @Test
    public void testThatUserCantRegisterWhenFullNameAreTheSame() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Happiness");
        registerRequest.setSurname("peace");
        registerRequest.setPassword("Peace#123");
        registerRequest.setEmail("happinessPeace2@gmail.com");
        registerRequest.setPhoneNumber("08023456745");
        contactManagementService.register(registerRequest);
        RegisterRequest registerRequestOla = new RegisterRequest();
        registerRequestOla.setFirstname("Happiness");
        registerRequestOla.setSurname("peace");
        registerRequestOla.setPassword("Peace#1234");
        registerRequestOla.setEmail("happiness@1267");
        registerRequestOla.setPhoneNumber("090345689");
        assertThrows(UserExistException.class, () -> contactManagementService.register(registerRequestOla));
    }

    @Test
    public void testThatUserThatRegisterCanLoginWithEmailAndPassword() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Abigeal");
        registerRequest.setSurname("peace");
        registerRequest.setPassword("Peace#123");
        registerRequest.setEmail("happinessPeace2@gmail.com");
        registerRequest.setPhoneNumber("0802345674");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("happinessPeace2@gmail.com");
        loginRequest.setPassword("Peace#123");
        contactManagementService.login(loginRequest);
        assertEquals(1, contactManagementRepository.count());
    }

    @Test
    public void testThatUserThatRegisterCanLoginWithWrongEmail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Happiness");
        registerRequest.setSurname("peace");
        registerRequest.setPassword("Peace#123");
        registerRequest.setEmail("happinessPeace21@gmail.com");
        registerRequest.setPhoneNumber("08023456745");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("happinessPeace@gmail.com");
        loginRequest.setPassword("Peace#123");
        assertThrows(InvalidDetailsException.class, () -> contactManagementService.login(loginRequest));
    }

    @Test
    public void testThatUserThatRegisterCanLoginWithEmailAndWrongPassword() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Jide");
        registerRequest.setSurname("wole");
        registerRequest.setPassword("Peace#123");
        registerRequest.setEmail("happinessPeace20@gmail.com");
        registerRequest.setPhoneNumber("08023456745");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("happinessPeace20@gmail.com");
        loginRequest.setPassword("Peace123");
        assertThrows(InvalidDetailsException.class, () -> contactManagementService.login(loginRequest));
    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLogin() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Daniel");
        registerRequest.setSurname("Ola");
        registerRequest.setPhoneNumber("09012345678");
        registerRequest.setEmail("danielola6@gmail.com");
        registerRequest.setPassword("Daniel@456");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("danielola6@gmail.com");
        loginRequest.setPassword("Daniel@456");
        contactManagementService.login(loginRequest);
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setFirstName("Bola");
        addContactRequest.setSurname("Soon");
        addContactRequest.setAddress("Kolacek ooo");
        addContactRequest.setCategoryName("Work");
        addContactRequest.setUserEmail("danielola6@gmail.com");
        addContactRequest.setEmail("ready45@gmail.com");
        addContactRequest.setPhoneNumber("08065432656");
        contactManagementService.addContact(addContactRequest);
        assertEquals(1, contactManagementRepository.count());
        assertEquals(1, contactRepository.count());

    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndEdit() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("feyi");
        registerRequest.setSurname("bola");
        registerRequest.setPhoneNumber("09034568712");
        registerRequest.setEmail("Feyibola@gmail.com");
        registerRequest.setPassword("Feyi@123");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Feyibola@gmail.com");
        loginRequest.setPassword("Feyi@123");
        contactManagementService.login(loginRequest);
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setFirstName("wumi");
        addContactRequest.setSurname("bukky");
        addContactRequest.setAddress("Sango");
        addContactRequest.setCategoryName("School");
        addContactRequest.setUserEmail("Feyibola@gmail.com");
        addContactRequest.setEmail("Wumibukky@gamil.com");
        addContactRequest.setPhoneNumber("07032167894");
        contactManagementService.addContact(addContactRequest);
        EditContactRequest editContactRequest = new EditContactRequest();
        editContactRequest.setOldContact("07032167894");
        editContactRequest.setNewContact("09045621343");
        editContactRequest.setUserEmail("Feyibola@gmail.com");
        assertEquals("09045621343", contactManagementService.editContact(editContactRequest).getPhoneNumber());
        assertEquals(1, contactManagementRepository.count());
        assertEquals(1, contactRepository.count());


    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndViewAllContacts() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("feyi");
        registerRequest.setSurname("bola");
        registerRequest.setPhoneNumber("09034568712");
        registerRequest.setEmail("Feyibola@gmail.com");
        registerRequest.setPassword("Feyi@123");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Feyibola@gmail.com");
        loginRequest.setPassword("Feyi@123");
        contactManagementService.login(loginRequest);
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setFirstName("wumi");
        addContactRequest.setSurname("bukky");
        addContactRequest.setAddress("Sango");
        addContactRequest.setCategoryName("School");
        addContactRequest.setUserEmail("Feyibola@gmail.com");
        addContactRequest.setEmail("Wumibukky@gmail.com");
        addContactRequest.setPhoneNumber("07032167894");
        contactManagementService.addContact(addContactRequest);
        AddContactRequest addContactRequestBola = new AddContactRequest();
        addContactRequestBola.setFirstName("Bola");
        addContactRequestBola.setSurname("Soon");
        addContactRequestBola.setAddress("Kolacek ooo");
        addContactRequestBola.setCategoryName("Work");
        addContactRequestBola.setUserEmail("Feyibola@gmail.com");
        addContactRequestBola.setEmail("ready45@gmail.com");
        addContactRequestBola.setPhoneNumber("08065432656");
        contactManagementService.addContact(addContactRequestBola);
        FindAllContactRequest findAllContactRequest = new FindAllContactRequest();
        findAllContactRequest.setSurname("bola");
        findAllContactRequest.setFirstName("feyi");
        findAllContactRequest.setEmail("Feyibola@gmail.com");
        assertEquals(1, contactManagementRepository.count());
        assertEquals(2, contactManagementService.viewAllContactBelongToUser(findAllContactRequest).size());

    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndViewAContacts() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("feyi");
        registerRequest.setSurname("bola");
        registerRequest.setPhoneNumber("09034568712");
        registerRequest.setEmail("Feyibola@gmail.com");
        registerRequest.setPassword("Feyi@123");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Feyibola@gmail.com");
        loginRequest.setPassword("Feyi@123");
        contactManagementService.login(loginRequest);
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setFirstName("wumi");
        addContactRequest.setSurname("bukky");
        addContactRequest.setAddress("Sango");
        addContactRequest.setCategoryName("School");
        addContactRequest.setUserEmail("Feyibola@gmail.com");
        addContactRequest.setEmail("Wumibukky@gmail.com");
        addContactRequest.setPhoneNumber("07032167894");
        contactManagementService.addContact(addContactRequest);
        FindAContactRequest findAContactRequest = new FindAContactRequest();
        findAContactRequest.setSurname("bukky");
        findAContactRequest.setFirstName("wumi");
        findAContactRequest.setUserEmail("Feyibola@gmail.com");
        assertEquals("wumi", contactManagementService.viewAContact(findAContactRequest).getFirstname());
        assertEquals("bukky", contactManagementService.viewAContact(findAContactRequest).getSurname());

    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndViewAllContactsInACategory() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Olamide");
        registerRequest.setSurname("Grace");
        registerRequest.setPhoneNumber("09065626032");
        registerRequest.setEmail("Olamidegrace@gmail.com");
        registerRequest.setPassword("Olamide@2244");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Olamidegrace@gmail.com");
        loginRequest.setPassword("Olamide@2244");
        contactManagementService.login(loginRequest);
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setFirstName("Hope");
        addContactRequest.setSurname("Joke");
        addContactRequest.setAddress("Lagos");
        addContactRequest.setCategoryName("School");
        addContactRequest.setUserEmail("Olamidegrace@gmail.com");
        addContactRequest.setEmail("Hopejoke67@gmail.com");
        addContactRequest.setPhoneNumber("08027893102");
        contactManagementService.addContact(addContactRequest);
        AddContactRequest addContactRequestOpe = new AddContactRequest();
        addContactRequestOpe.setFirstName("Bola");
        addContactRequestOpe.setSurname("Soon");
        addContactRequestOpe.setAddress("Kolacek ooo");
        addContactRequestOpe.setCategoryName("School");
        addContactRequestOpe.setUserEmail("Olamidegrace@gmail.com");
        addContactRequestOpe.setEmail("ready45@gmail.com");
        addContactRequestOpe.setPhoneNumber("08065432656");
        contactManagementService.addContact(addContactRequestOpe);
        FindAllContactsInACategory findAllContactsInACategory = new FindAllContactsInACategory();
        findAllContactsInACategory.setEmail("Olamidegrace@gmail.com");
        findAllContactsInACategory.setCategoryName("School");
        assertEquals(1, contactManagementRepository.count());
        assertEquals(2, contactManagementService.viewAllContactBelongInCategory(findAllContactsInACategory).size());


    }

    @Test
    public void testThatUserCanAddContactAfterUserRegisterAndLoginAndEditContact() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("feyi");
        registerRequest.setSurname("bola");
        registerRequest.setPhoneNumber("09034568712");
        registerRequest.setEmail("Feyibola@gmail.com");
        registerRequest.setPassword("Feyi@123");
        contactManagementService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Feyibola@gmail.com");
        loginRequest.setPassword("Feyi@123");
        contactManagementService.login(loginRequest);
        AddContactRequest addContactRequest = new AddContactRequest();
        addContactRequest.setFirstName("wumi");
        addContactRequest.setSurname("bukky");
        addContactRequest.setAddress("Sango");
        addContactRequest.setCategoryName("School");
        addContactRequest.setUserEmail("Feyibola@gmail.com");
        addContactRequest.setEmail("Wumibukky@gmail.com");
        addContactRequest.setPhoneNumber("07032167894");
        contactManagementService.addContact(addContactRequest);
        EditContactInfoRequest editContactInfoRequest = new EditContactInfoRequest();
        editContactInfoRequest.setSurname("wumis");
        editContactInfoRequest.setFirstName("bukkys");
        editContactInfoRequest.setAddress("ota");
        editContactInfoRequest.setEmail("Wumibukkys@gmail.com");
        editContactInfoRequest.setUserEmail("Feyibola@gmail.com");
        editContactInfoRequest.setPhoneNumber("07032167894");
        editContactInfoRequest.setCategoryName("Work");
       assertEquals("wumis",contactManagementService.editContactInfo(editContactInfoRequest).getSurname());
       assertEquals("bukkys",contactManagementService.editContactInfo(editContactInfoRequest).getFirstname());
        assertEquals("ota",contactManagementService.editContactInfo(editContactInfoRequest).getAddress());
        assertEquals("Wumibukkys@gmail.com",contactManagementService.editContactInfo(editContactInfoRequest).getEmail());
        assertEquals("Feyibola@gmail.com",contactManagementService.editContactInfo(editContactInfoRequest).getUserEmail());
        assertEquals("07032167894",contactManagementService.editContactInfo(editContactInfoRequest).getPhoneNumber());
        assertEquals("Work",contactManagementService.editContactInfo(editContactInfoRequest).getCategoryName());





    }
}