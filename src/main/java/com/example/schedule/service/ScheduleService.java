package com.example.schedule.service;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.NoContentException;
import com.example.schedule.repository.AuthorRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto) {
        Author foundAuthor = findAuthorByEmailOrElseThrow(requestDto.getEmail());

        Schedule schedule = new Schedule(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getScheduledDate()
        );

        schedule.setAuthor(foundAuthor);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(),savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getScheduledDate(), foundAuthor.getEmail());

    }

    private Author findAuthorByEmailOrElseThrow(String email) {
        return authorRepository.findByEmail(email)
                .orElseThrow(()->
                        new NoContentException("Email Does not Exist. Email = " + email)
                );
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getScheduledDate(),
                        schedule.getAuthor().getEmail()
                )).toList();
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule foundSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoContentException("ID Does Not Exist. ID = " + id));
        Author author = foundSchedule.getAuthor();
        return new ScheduleResponseDto(
                foundSchedule.getId(),
                foundSchedule.getTitle(),
                foundSchedule.getContents(),
                foundSchedule.getScheduledDate(),
                author.getEmail()
        );
    }

    public void delete(Long id) {
        Schedule foundSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoContentException("ID Does Not Exist. ID = " + id));
        scheduleRepository.delete(foundSchedule);
    }
}
