package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PagingScheduleResponseDto {
    private Long id;

    private String title;

    private String contents;

    private LocalDate scheduledDate;

    private String email;

    private Long comments;


    public  PagingScheduleResponseDto(Schedule schedule, Long comments){
        this.id=schedule.getId();
        this.title=schedule.getTitle();
        this.contents=schedule.getContents();
        this.scheduledDate=schedule.getScheduledDate();
        this.email=schedule.getAuthor().getEmail();
        this.comments=comments;
    }
}
