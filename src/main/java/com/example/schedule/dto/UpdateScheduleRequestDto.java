package com.example.schedule.dto;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateScheduleRequestDto {

    private final String title;

    private final String contents;

    @Future(message = "Scheduled Date must be in the future")
    private final LocalDate scheduledDate;
}
