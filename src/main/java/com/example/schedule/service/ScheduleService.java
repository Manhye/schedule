package com.example.schedule.service;

import com.example.schedule.common.Const;
import com.example.schedule.config.PasswordEncoder;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.ForbiddenException;
import com.example.schedule.exception.NoContentException;
import com.example.schedule.repository.AuthorRepository;
import com.example.schedule.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;


    public ScheduleResponseDto createSchedule(CreateScheduleRequestDto requestDto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object dto = session.getAttribute(Const.LOGIN_AUTHOR);

        String email = null;

        if(dto instanceof AuthorResponseDto){
            email = ((AuthorResponseDto) dto).getEmail();
        }

        Author foundAuthor = findAuthorByEmailOrElseThrow(email);

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

    public Page<ScheduleResponseDto> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modified_at"));

            return scheduleRepository.findAll(pageable)
                .map(ScheduleResponseDto::new);
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

    public void delete(Long id, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object dto = session.getAttribute(Const.LOGIN_AUTHOR);

        String email = null;
        if(dto instanceof AuthorResponseDto){
            email = ((AuthorResponseDto) dto).getEmail();
        }


        Schedule foundSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoContentException("ID Does Not Exist. ID = " + id));

        // Only the creator can delete the schedule.
        if(!foundSchedule.getAuthor().getEmail().equals(email)){
            throw new ForbiddenException(
                    "You are not authorized to delete this schedule."
            );
        }

        scheduleRepository.delete(foundSchedule);
    }

    @Transactional
    public void updateSchedule(Long id, UpdateScheduleRequestDto requestDto, HttpServletRequest request) {

        Schedule foundSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoContentException("Comment Does Not Exist. ID = " + id));
        Author foundAuthor = authorRepository.findById(foundSchedule.getId())
                .orElseThrow(() -> new NoContentException("Author Does Not Exist."));

        HttpSession session = request.getSession();
        Object dto = session.getAttribute(Const.LOGIN_AUTHOR);

        String email = null;
        if(dto instanceof AuthorResponseDto){
            email = ((AuthorResponseDto) dto).getEmail();
        }


        // Only the creator can update the schedule.
        if(!foundAuthor.getEmail().equals(email)){
            throw new ForbiddenException(
                    "You are not authorized to update this schedule."
            );
        }

        if(requestDto.getTitle() != null && !requestDto.getTitle().isBlank()){
            foundSchedule.setTitle(requestDto.getTitle());
        }

        if(requestDto.getContents() != null && !requestDto.getContents().isBlank()){
            foundSchedule.setContents(requestDto.getContents());
        }

        if(requestDto.getScheduledDate() != null){
            foundSchedule.setScheduledDate(requestDto.getScheduledDate());
        }

    }

}
