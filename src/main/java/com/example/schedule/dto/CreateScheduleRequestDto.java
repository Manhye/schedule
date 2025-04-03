package com.example.schedule.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class CreateScheduleRequestDto {

    @NotBlank(message = "Title is required")
    private final String title;

    @NotBlank(message = "Contents is required")
    private final String contents;

    @Future(message = "Scheduled Date must be in the future")
    private final LocalDate scheduledDate;


}
