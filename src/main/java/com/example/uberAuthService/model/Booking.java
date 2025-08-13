package com.example.uberAuthService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Booking extends BaseModel{

//    @OneToOne
//    private Review review;

    @Enumerated(value = EnumType.STRING)
    private BookingReviewStatus bookingReviewStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDuration;

    //    @JsonBackReference
    @ManyToOne //(fetch = FetchType.LAZY)
    private Driver driver;

    @ManyToOne  //(fetch = FetchType.LAZY)
    private Passenger passenger;
}
