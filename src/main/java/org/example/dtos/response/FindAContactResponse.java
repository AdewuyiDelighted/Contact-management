package org.example.dtos.response;

import lombok.Data;
import org.example.data.model.Contact;

@Data
public class FindAContactResponse {
    private Contact contact;
    private String message;
}
