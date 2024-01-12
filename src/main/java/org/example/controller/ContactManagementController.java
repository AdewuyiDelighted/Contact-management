package org.example.controller;

import org.example.dtos.request.*;
import org.example.dtos.response.*;
import org.example.exceptions.ContactManagementExceptions;
import org.example.services.ContactManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContactManagementController {
    @Autowired
    private ContactManagementService contactManagementService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterRequestResponse registerRequestResponse = new RegisterRequestResponse();
        try {
            contactManagementService.register(registerRequest);
            registerRequestResponse.setMessage("Account created successfully");
            return new ResponseEntity<>(new ApiResponse(true, registerRequestResponse), HttpStatus.CREATED);
        } catch (ContactManagementExceptions ex) {
            registerRequestResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerRequestResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            contactManagementService.login(loginRequest);
            loginResponse.setMessage("Account login");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            loginResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, loginResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addContact")
    public ResponseEntity<ApiResponse> addContact(@RequestBody AddContactRequest addContactRequest) {
        AddContactResponse addContactResponse = new AddContactResponse();
        try {
            contactManagementService.addContact(addContactRequest);
            addContactResponse.setMessage("Contact added");
            return new ResponseEntity<>(new ApiResponse(true, addContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            addContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, addContactResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/editContact")
    public ResponseEntity<ApiResponse> editContact(@RequestBody EditContactInfoRequest editContactInfoRequest) {
        EditContactInfoResponse editContactInfoResponse = new EditContactInfoResponse();
        try {
            contactManagementService.editContactInfo(editContactInfoRequest);
            editContactInfoResponse.setMessage("Contact updated to date");
            return new ResponseEntity<>(new ApiResponse(true, editContactInfoResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            editContactInfoResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, editContactInfoResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/editUserContact")
    public ResponseEntity<ApiResponse> editUserContact(@RequestBody EditUserInfoRequest editUserInfoRequest) {
        EditContactUserResponse editContactUserResponse = new EditContactUserResponse();
        try {
            contactManagementService.editUserInfo(editUserInfoRequest);
            editContactUserResponse.setMessage("Contact updated to date");
            return new ResponseEntity<>(new ApiResponse(true, editContactUserResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            editContactUserResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, editContactUserResponse), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/viewAContact")
    public ResponseEntity<ApiResponse> viewAContact(@RequestBody FindAContactRequest findAContactRequest) {
        FindAContactResponse findAContactResponse = new FindAContactResponse();
        try {
            findAContactResponse.setContact(contactManagementService.viewAContact(findAContactRequest));
            return new ResponseEntity<>(new ApiResponse(true, findAContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            findAContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, findAContactResponse), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/viewAllContactInCategory")
    public ResponseEntity<ApiResponse> viewAllContactInCategory(@RequestBody FindAllContactsInACategory findAllContactsInACategory) {
        FindAllContactInACategoryResponse findAllContactInACategoryResponse = new FindAllContactInACategoryResponse();
        try {
            //contactManagementService.viewAllContactBelongInCategory(findAllContactRequest);
            findAllContactInACategoryResponse.setContacts(contactManagementService.viewAllContactBelongInCategory(findAllContactsInACategory));
            return new ResponseEntity<>(new ApiResponse(true, findAllContactInACategoryResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            findAllContactInACategoryResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, findAllContactInACategoryResponse), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/viewAllContact")
    public ResponseEntity<ApiResponse> viewAllContact(@RequestBody FindAllContactRequest findAllContactRequest) {
        FindAllContactResponse findAllContactResponse = new FindAllContactResponse();
        try {
            findAllContactResponse.setContacts(contactManagementService.viewAllContactBelongToUser(findAllContactRequest));
            return new ResponseEntity<>(new ApiResponse(true, findAllContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            findAllContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, findAllContactResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/blockContact")
    public ResponseEntity<ApiResponse> blockContact(@RequestBody BlockContactRequest blockContactRequest) {
        BlockContactResponse blockContactResponse = new BlockContactResponse();
        try {
            contactManagementService.blockContact(blockContactRequest);
            blockContactResponse.setMessage("Contact blocked");
            return new ResponseEntity<>(new ApiResponse(true, blockContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            blockContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, blockContactResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unBlockContact")
    public ResponseEntity<ApiResponse> unBlockContact(@RequestBody UnblockContactRequest unblockContactRequest) {
        UnblockContactResponse unblockContactResponse = new UnblockContactResponse();
        try {
            contactManagementService.unblockContact(unblockContactRequest);
            unblockContactResponse.setMessage("Contact unblocked");
            return new ResponseEntity<>(new ApiResponse(true, unblockContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            unblockContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, unblockContactResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAContact")
    public ResponseEntity<ApiResponse> deleteAContact(@RequestBody DeleteAContactRequest deleteAContactRequest) {
        DeleteAContactResponse deleteAContactResponse = new DeleteAContactResponse();
        try {
            contactManagementService.deleteAContact(deleteAContactRequest);
            deleteAContactResponse.setMessage("Contact deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteAContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            deleteAContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, deleteAContactResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAllContactInCategory")
    public ResponseEntity<ApiResponse> deleteAllContactInCategory(@RequestBody DeleteAllContactsInACategory deleteAllContactsInACategory) {
        DeleteAllContactInCategoryResponse deleteAllContactInCategoryResponse = new DeleteAllContactInCategoryResponse();
        try {
            contactManagementService.deleteAllContactBelongInCategory(deleteAllContactsInACategory);
            deleteAllContactInCategoryResponse.setMessage("All contact in category deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteAllContactInCategoryResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            deleteAllContactInCategoryResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, deleteAllContactInCategoryResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAllContact")
    public ResponseEntity<ApiResponse> deleteAllContact(@RequestBody DeleteAllContactRequest deleteAllContactRequest) {
        DeleteAllContactResponse deleteAllContactResponse = new DeleteAllContactResponse();
        try {
            contactManagementService.deleteAllContact(deleteAllContactRequest);
            deleteAllContactResponse.setMessage("All contact deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteAllContactResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            deleteAllContactResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, deleteAllContactResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAccount/{email}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable("email") String email) {
        DeleteAccountResponse deleteAccountResponse = new DeleteAccountResponse();
        try {
            contactManagementService.deleteAccount(email);
            deleteAccountResponse.setMessage("Account deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteAccountResponse), HttpStatus.OK);
        } catch (ContactManagementExceptions ex) {
            deleteAccountResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, deleteAccountResponse), HttpStatus.BAD_REQUEST);
        }
    }


}
