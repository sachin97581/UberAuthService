package com.example.uberAuthService.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")  // we @Value annotation to get variables from application.property file
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     *
     * this method use to create brand-new JWT Token for use based on payload
     * @return
     */

    // learn about .subject() method , what is it why we use here , what it's work

    public String createToken(Map<String , Object> payload , String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSignKey())
                .compact();
    }

    public String createToken(String email){
        return createToken(new HashMap<>() , email);
    }

    // this will get claim(get all data from token) all data
    public Claims extractAllPayloads(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
//                .parseClaimsJwt(token)
                .parseClaimsJws(token)   // ✅ use parseClaimsJws instead of parseClaimsJwt
                .getBody();
    }

    // extract all claims
    public <T> T extractClaims(String token , Function<Claims , T> claimRecover){
        final Claims claims = extractAllPayloads(token);
        return claimRecover.apply(claims);
    }

    public Date extractExpiration(String token){
        return extractClaims(token , Claims::getExpiration);
    }

    public String extractEmail(String token){
        return extractClaims(token , Claims::getSubject);
    }

    /**
     * this method check that token has expired before current time stamp or not
     * @param token JWT token
     * @return true if token expired or false
     */

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public Boolean validateToken(String token , String email){
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(email) && !isTokenExpired(token));
    }

    public Object extractPhoneNumber(String token , String payloadKey){
        Claims claim = extractAllPayloads(token);
        return claim.get(payloadKey);  // no need to cast to Object explicitly
    }


    @Override
    public void run(String... args) throws Exception {
        Map<String , Object> mp = new HashMap<>();

        mp.put("email" , "sine@gmail.com");
        mp.put("phoneNumber" , "1234567801");

        String result = createToken(mp , "Sachin");
        System.out.println("The generated Token is " + result);
        System.out.println(extractPhoneNumber(result , "phoneNumber").toString());
    }
}
