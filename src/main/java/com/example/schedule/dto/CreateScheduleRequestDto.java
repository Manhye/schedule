package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class CreateScheduleRequestDto {

    private final String title;

    private final String contents;

    private final LocalDate scheduledDate;

    private final String email;

}
