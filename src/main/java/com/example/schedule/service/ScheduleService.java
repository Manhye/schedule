package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.CreateScheduleResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.AuthorRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;

    public CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto) {
        Author foundAuthor = findAuthorByEmailOrElseThrow(requestDto.getEmail());

        Schedule schedule = new Schedule(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getScheduledDate()
        );

        schedule.setAuthor(foundAuthor);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(schedule.getId(),schedule.getTitle(), schedule.getContents(), schedule.getScheduledDate(), foundAuthor.getEmail());

    }

    private Author findAuthorByEmailOrElseThrow(String email) {
        return authorRepository.findByEmail(email)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NO_CONTENT, "Email Does not Exist. Email = " + email
                        )
                );
    }
}
