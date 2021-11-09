package com.example.ProjectB.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private String address;
    private String phoneNumber;
    private String accountBalance;
}
