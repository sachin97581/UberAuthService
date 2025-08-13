package com.example.uberAuthService.model;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseModel { // we create basemodel class asbstrect because we do not want make's it's object one can able to create it's object aslo

    // we use @Id annotation to create primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY means auto increment
    protected Long id;


    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP ) // this annotation tells spring about the format of date object to be stored i.e. date/time / timestamp
    @CreatedDate // this annotation tells spring that only handle it for object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP )
    @LastModifiedDate // this annotation tells spring that only handle it for object updation
    protected Date updatedAt;
}
