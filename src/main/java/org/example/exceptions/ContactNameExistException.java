package org.example.exceptions;

public class ContactNameExistException extends ContactManagementExceptions{
    public ContactNameExistException(String message) {
        super(message);
    }
}
