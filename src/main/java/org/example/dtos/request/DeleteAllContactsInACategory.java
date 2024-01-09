package org.example.dtos.request;

import lombok.Data;

@Data
public class DeleteAllContactsInACategory {
    private String email;
    private String categoryName;
}
