package org.example.exceptions;

public class ContactDoesntExistException extends ContactManagementExceptions{
    public ContactDoesntExistException(String message) {
        super(message);
    }
}
