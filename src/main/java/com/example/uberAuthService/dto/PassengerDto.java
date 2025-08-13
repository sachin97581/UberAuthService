package com.example.uberAuthService.dto;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {

    private int id;

    private String name;

    private String email;

    private String phoneNumber;

    private String password;


    private Date createdAt;
}
