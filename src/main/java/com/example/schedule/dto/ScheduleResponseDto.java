package com.example.schedule.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {


    private Long id;

    private String title;

    private String contents;

    private LocalDate scheduledDate;

    private String email;


}
