package com.example.schedule.dto;


import com.example.schedule.entity.Schedule;
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

    public  ScheduleResponseDto(Schedule schedule){
        this.id=schedule.getId();
        this.title=schedule.getTitle();
        this.contents=schedule.getContents();
        this.scheduledDate=schedule.getScheduledDate();
    }


}
