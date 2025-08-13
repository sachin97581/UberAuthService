package com.example.uberAuthService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" , "bookings"})
public class Driver extends BaseModel{

    private String name;

    private String phoneNumber;

    @Column(nullable = false , unique = true)
    private String licenceNumber;

    //    @JsonManagedReference
    @OneToMany(mappedBy = "driver")
    private List<Booking> bookings = new ArrayList<>();
}

