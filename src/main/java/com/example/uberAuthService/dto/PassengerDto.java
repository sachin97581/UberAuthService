package com.example.uberAuthService.dto;


import com.example.uberAuthService.model.Passenger;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PassengerDto {

    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private String password;

    private Date createdAt;

    public static PassengerDto from(Passenger p){
        PassengerDto result = PassengerDto.builder()
                .id(p.getId().toString())
                .email(p.getEmail())
                .name(p.getName())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .createdAt(p.getCreatedAt())
                .build();
        return result;
    }
}
