package com.example.schedule.controller;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid CreateScheduleRequestDto requestDto
            ){
        ScheduleResponseDto dto = scheduleService.createSchedule(requestDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<ScheduleResponseDto> scheduleResponseDto = scheduleService.findAll(page, size);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id){
        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto,
            HttpServletRequest request
    ){
        scheduleService.updateSchedule(id, requestDto, request);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request){
        scheduleService.delete(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
