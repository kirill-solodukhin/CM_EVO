package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInformation
{
    private String firstName;
    private String lastName;
    private String patronymic;
    private boolean online;
    private int userId;
}
