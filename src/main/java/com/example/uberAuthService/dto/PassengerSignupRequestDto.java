package com.example.uberAuthService.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerSignupRequestDto {


    private String name;

    private String phoneNumber;

    private String email;

    private String password;

}
