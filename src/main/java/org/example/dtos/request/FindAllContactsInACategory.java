package org.example.dtos.request;

import lombok.Data;

@Data
public class FindAllContactsInACategory {
    private String email;
    private String categoryName;
}
