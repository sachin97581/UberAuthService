package com.example.uberAuthService.cotroller;


import com.example.uberAuthService.dto.AuthRequestDto;
import com.example.uberAuthService.dto.PassengerDto;
import com.example.uberAuthService.dto.PassengerSignupRequestDto;
import com.example.uberAuthService.service.AuthService;
import com.example.uberAuthService.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    @Autowired
   private final AuthService authService;

   @Autowired
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

   public AuthController(AuthService authService , AuthenticationManager authenticationManager , JwtService jwtService){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerDto response = authService.signupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signin(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        System.out.println("Request received " + authRequestDto.getEmail() +" " + authRequestDto.getPassword() );
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail() , authRequestDto.getPassword()));
        String jwtToken = jwtService.createToken(authRequestDto.getEmail());
        ResponseCookie responseCookie = ResponseCookie.from("jwtToken" , jwtToken)
                        .httpOnly(true)
                        .path("/")
                        .secure(false)
                        .maxAge(cookieExpiry)
                        .build();
        response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        if (authentication.isAuthenticated()){
            return new ResponseEntity<>( jwtToken, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Success full sign-in", HttpStatus.OK);
    }
}
