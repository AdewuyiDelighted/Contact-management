package org.example.utils;

import org.example.data.model.Contact;
import org.example.data.model.ContactManagement;
import org.example.data.repositories.ContactRepository;
import org.example.dtos.request.AddContactRequest;
import org.example.dtos.request.RegisterRequest;
import org.example.exceptions.ContactDoesntExistException;
import org.example.exceptions.ContactManagementExceptions;
import org.example.exceptions.InvalidDetailsException;
import org.example.exceptions.PasswordTooWeakException;
import org.hibernate.type.TrueFalseConverter;

import java.util.Optional;
import java.util.regex.Pattern;

public class Verification {


    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z 0-9_+&*-]+(?:\\." + "[a-zA-Z 0-9_+&*-]+)*@" + "(?:[a-zA-Z 0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        return email.matches(regex);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+")) return phoneNumber.matches("[+][1-9][0-9]{6,12}");
        else return phoneNumber.matches("0[7-9][0-1][0-9]{8}");
    }

    public static void passwordChecker(RegisterRequest registerRequest) {
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[@#$%^&-+=()])" + "(?=\\S+$).{8,20}$";
        if (!Pattern.matches(regex, registerRequest.getPassword()))
            throw new PasswordTooWeakException("Password too weak");
    }
}
