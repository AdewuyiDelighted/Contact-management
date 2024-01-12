package org.example.dtos.response;

import lombok.Data;
import org.example.data.model.Contact;

import java.util.List;

@Data
public class FindAllContactInACategoryResponse {
    private List<Contact> contacts;
    private String message;
}
