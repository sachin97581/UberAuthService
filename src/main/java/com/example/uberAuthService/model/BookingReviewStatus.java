package com.example.uberAuthService.model;

import jakarta.persistence.Enumerated;


public enum BookingReviewStatus {

    SCHEDULE,

    CANCELLED,

    CAB_ARRIVED,

    ASSIGNING_DRIVER,

    IN_RIDE,

    COMPLETE
}
