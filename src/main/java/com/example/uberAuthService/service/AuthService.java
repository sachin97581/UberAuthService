package com.example.uberAuthService.service;


import com.example.uberAuthService.Repository.PassengerRepository;
import com.example.uberAuthService.dto.PassengerDto;
import com.example.uberAuthService.dto.PassengerSignupRequestDto;
import com.example.uberAuthService.model.Passenger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PassengerRepository passengerRepository;

    AuthService(PassengerRepository passengerRepository , BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passengerRepository = passengerRepository;
    }


    public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger = Passenger.builder()
                .name(passengerSignupRequestDto.getName())
                .email(passengerSignupRequestDto.getEmail())
                .phoneNumber(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPhoneNumber()))
                .password(passengerSignupRequestDto.getPassword())
                .build();

       Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDto.from(newPassenger);
    }
}
