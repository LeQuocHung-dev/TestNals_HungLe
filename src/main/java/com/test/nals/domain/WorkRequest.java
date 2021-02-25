package com.test.nals.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkRequest {

    String id;

    @NotNull(message = "WorkName must not empty or null")
    String workName;

    LocalDateTime startingDate;

    LocalDateTime endingDate;

    String status;
}
