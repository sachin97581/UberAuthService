package com.example.uberAuthService.service;

import com.example.uberAuthService.Repository.PassengerRepository;
import com.example.uberAuthService.helper.AuthPassengerDetail;
import com.example.uberAuthService.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * this class responsible for loading the user in the form of UserDetail object for auth.
 */

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

//    UserDetailServiceImp(PassengerRepository passengerRepository){
//        this.passengerRepository = passengerRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String email ) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);
        if(passenger.isPresent()){
            return new AuthPassengerDetail(passenger.get());
        }else {
            throw new UsernameNotFoundException("Passenger not found by this email");
        }
    }
}
