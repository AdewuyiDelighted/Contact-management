package org.example.services;

import org.example.data.model.Contact;
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

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testThatUserCanRegisterLoginAndAddContactAndEditContact() {
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
        EditContactInfoRequest editContactInfoRequest = new EditContactInfoRequest();
        editContactInfoRequest.setFormerSurname("Ola");
        editContactInfoRequest.setFormerFirstName("Daniel");
        editContactInfoRequest.setNewSurname("Jenny");
        editContactInfoRequest.setNewSurname("Chiamanda");
        editContactInfoRequest.setUserEmail("danielola6@gmail.com");
        editContactInfoRequest.setEmail("ready45@gmail.com");
        editContactInfoRequest.setAddress("Ijebu");
        editContactInfoRequest.setCategoryName("Street");
        editContactInfoRequest.setPhoneNumber("08065432656");
        Contact contact = contactManagementService.editContactInfo(editContactInfoRequest);
        assertNotNull(contact);
    }


    @Test

    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndDeleteAContact() {
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
        addContactRequestOpe.setCategoryName("church");
        addContactRequestOpe.setUserEmail("Olamidegrace@gmail.com");
        addContactRequestOpe.setEmail("ready45@gmail.com");
        addContactRequestOpe.setPhoneNumber("08065432656");
        contactManagementService.addContact(addContactRequestOpe);
        DeleteAContactRequest deleteAContactRequest = new DeleteAContactRequest();
        deleteAContactRequest.setSurname("Soon");
        deleteAContactRequest.setFirstName("Bola");
        deleteAContactRequest.setUserEmail("Olamidegrace@gmail.com");
        contactManagementService.deleteAContact(deleteAContactRequest);
        assertEquals(1, contactManagementRepository.count());
        assertEquals(1, contactRepository.count());

    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndDeletellContactsInACategory() {
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
        DeleteAllContactsInACategory deleteAllContactsInACategory = new DeleteAllContactsInACategory();
        deleteAllContactsInACategory.setEmail("Olamidegrace@gmail.com");
        deleteAllContactsInACategory.setCategoryName("School");
        contactManagementService.deleteAllContactBelongInCategory(deleteAllContactsInACategory);
        assertEquals(1, contactManagementRepository.count());
        assertEquals(0, contactRepository.count());
    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndDeleteAllContacts() {
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
        DeleteAllContactRequest deleteAllContactRequest = new DeleteAllContactRequest();
        deleteAllContactRequest.setEmail("Feyibola@gmail.com");
        contactManagementService.deleteAllContact(deleteAllContactRequest);
        assertEquals(1, contactManagementRepository.count());
        assertEquals(0, contactRepository.count());

    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndBlockAContact() {
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
        BlockContactRequest blockContactRequest = new BlockContactRequest();
        blockContactRequest.setFirstname("Hope");
        blockContactRequest.setSurname("Joke");
        blockContactRequest.setEmail("Olamidegrace@gmail.com");
        assertTrue(contactManagementService.blockContact(blockContactRequest).isBlocked());
        assertEquals(1, contactManagementRepository.count());
        assertEquals(1, contactRepository.count());
    }

    @Test
    public void testThatUserCanAddContactAfterThatRegisterAndLoginAndBlockAContactAndUnblockTheContact() {
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
        BlockContactRequest blockContactRequest = new BlockContactRequest();
        blockContactRequest.setFirstname("Hope");
        blockContactRequest.setSurname("Joke");
        blockContactRequest.setEmail("Olamidegrace@gmail.com");
        assertTrue(contactManagementService.blockContact(blockContactRequest).isBlocked());
        UnblockContactRequest unblockContactRequest = new UnblockContactRequest();
        unblockContactRequest.setFirstname("Hope");
        unblockContactRequest.setSurname("Joke");
        unblockContactRequest.setEmail("Olamidegrace@gmail.com");
        assertFalse(contactManagementService.unblockContact(unblockContactRequest).isBlocked());
        assertEquals(1, contactManagementRepository.count());
        assertEquals(1, contactRepository.count());

    }

    @Test
    public void testThatUserCanRegisterContactManagementLoginAndUpdateInfo() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Delighted");
        registerRequest.setSurname("Adewuyi");
        registerRequest.setPassword("Adewuyi@123");
        registerRequest.setEmail("deborahdelighted5@gmail.com");
        registerRequest.setPhoneNumber("08072034442");
        contactManagementService.register(registerRequest);
        assertEquals(1, contactManagementRepository.count());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("deborahdelighted5@gmail.com");
        loginRequest.setPassword("Adewuyi@123");
        contactManagementService.login(loginRequest);
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        editUserInfoRequest.setFormerFirstname("Delighted");
        editUserInfoRequest.setFormerSurname("Adewuyi");
        editUserInfoRequest.setNewFirstname("Deborah");
        editUserInfoRequest.setNewSurname("G-Adewuyi");
        editUserInfoRequest.setNewEmail("deborahdelightedEmily5@gmail.com");
        editUserInfoRequest.setFormerEmail("deborahdelighted5@gmail.com");
        editUserInfoRequest.setFormerPhoneNumber("08072034442");
        editUserInfoRequest.setNewPhoneNumber("09065626032");
      assertEquals("G-Adewuyi", contactManagementService.editUserInfo(editUserInfoRequest).getSurname());
    }

    @Test
    public void testThatUserCanRegisterContactManagementLoginAndDeleteAccount() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Delighted");
        registerRequest.setSurname("Adewuyi");
        registerRequest.setPassword("Adewuyi@123");
        registerRequest.setEmail("deborahdelighted5@gmail.com");
        registerRequest.setPhoneNumber("08072034442");
        contactManagementService.register(registerRequest);
        assertEquals(1, contactManagementRepository.count());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("deborahdelighted5@gmail.com");
        loginRequest.setPassword("Adewuyi@123");
        contactManagementService.login(loginRequest);
        contactManagementService.deleteAccount("deborahdelighted5@gmail.com");
        assertEquals(0,contactManagementRepository.count());
    }
}