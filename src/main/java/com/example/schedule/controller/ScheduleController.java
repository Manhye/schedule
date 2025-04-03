package com.example.schedule.controller;

import com.example.schedule.dto.CreateScheduleRequestDto;
import com.example.schedule.dto.PagingScheduleResponseDto;
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

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Creates a new schedule.
     *
     * This method receives schedule details from the request body and creates a new schedule.
     * The created schedule is returned in the response.
     *
     * @param requestDto The {@link CreateScheduleRequestDto} containing the schedule details.
     * @param request The {@link HttpServletRequest} object, which may contain additional context.
     * @return {@link ResponseEntity} containing the created schedule as {@link ScheduleResponseDto}
     *         with HTTP status 200 (OK) upon success.
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid CreateScheduleRequestDto requestDto,
            HttpServletRequest request
            ){
        ScheduleResponseDto dto = scheduleService.createSchedule(requestDto, request);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Retrieves a paginated list of schedules.
     *
     * This method gets all schedules with pagination support.
     * Clients can specify the page number and size using query parameters.
     *
     * @param page The page number to retrieve (default: 0).
     * @param size The number of schedules per page (default: 10).
     * @return {@link ResponseEntity} containing a {@link Page} of {@link ScheduleResponseDto}
     *         with HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<Page<PagingScheduleResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<PagingScheduleResponseDto> pagingScheduleResponseDto = scheduleService.findAll(page, size);

        return new ResponseEntity<>(pagingScheduleResponseDto, HttpStatus.OK);
    }

    /**
     * Retrieves a schedule by its unique identifier.
     *
     * This method gets a specific schedule based on the provided ID.
     * If the schedule is found, it returns the corresponding {@link ScheduleResponseDto}.
     *
     * @param id The unique identifier of the schedule.
     * @return {@link ResponseEntity} containing the {@link ScheduleResponseDto}
     *         with HTTP status 200 (OK).
     * @throws com.example.schedule.exception.NoContentException If the schedule doesn't exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id){
        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    /**
     * Updates an existing schedule with the provided details.
     *
     * This method modifies a schedule identified by the given ID, using the data from
     * the {@link UpdateScheduleRequestDto}. The request information is also passed
     * for additional processing if needed.
     *
     * @param id The unique identifier of the schedule to be updated.
     * @param requestDto The DTO containing updated schedule details.
     * @param request The HTTP request containing additional context information.
     * @return {@link ResponseEntity} with HTTP status 200 (OK) if the update is successful.
     * @throws com.example.schedule.exception.NoContentException If the schedule or author don't exist.
     * @throws com.example.schedule.exception.ForbiddenException If the schedule's author and request's author doesn't match.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto,
            HttpServletRequest request
    ){
        scheduleService.updateSchedule(id, requestDto, request);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * Deletes a schedule identified by the given ID.
     *
     * This method removes a schedule from the system based on the provided ID.
     * The HTTP request is passed to verify user permissions or for logging purposes.
     *
     * @param id The unique identifier of the schedule to be deleted.
     * @param request The HTTP request containing authentication or additional context.
     * @return {@link ResponseEntity} with HTTP status 200 (OK) if deletion is successful.
     * @throws com.example.schedule.exception.NoContentException If the id doesn't exist.
     * @throws com.example.schedule.exception.ForbiddenException If the user tries to remove other's schedule.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request){
        scheduleService.delete(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
