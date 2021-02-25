package com.test.nals.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Work {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "work_Name")
    String workName;

    @Column(name = "starting_Date")
    LocalDateTime startingDate;

    @Column(name = "ending_Date")
    LocalDateTime endingDate;

    @Column(name = "status")
    String status;
}
