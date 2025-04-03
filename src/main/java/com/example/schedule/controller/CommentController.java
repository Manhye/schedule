package com.example.schedule.controller;

import com.example.schedule.dto.CommentRequestDto;
import com.example.schedule.dto.CommentResponseDto;
import com.example.schedule.dto.UpdateCommentRequestDto;
import com.example.schedule.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Creates a new comment for a specific schedule.
     *
     * This method allows users to add a comment to a schedule identified by {@code scheduleId}.
     * The comment details are provided in the request body. The request also contains user
     * session details for authentication and authorization purposes.
     *
     * @param requestDto The DTO containing the comment content.
     * @param scheduleId The ID of the schedule to which the comment is being added.
     * @param request The HTTP request containing session information.
     * @return A ResponseEntity containing the created comment details with HTTP status 200.
     * @throws com.example.schedule.exception.NoContentException If the author or the schedule doesn't exist.
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto requestDto,
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ){


        CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto, request);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    /**
     * Retrieves all comments associated with a specific schedule.
     *
     * This method gets a list of comments linked to the given {@code scheduleId}.
     * It allows users to view all comments related to a particular schedule.
     *
     * @param scheduleId The ID of the schedule whose comments are to be retrieved.
     * @return A ResponseEntity containing a list of CommentResponseDto objects with HTTP status 200.
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId){
        List<CommentResponseDto> commentResponseDtoList = commentService.getComments(scheduleId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }


    /**
     * Updates a specific comment associated with a schedule.
     *
     * This method modifies the content of an existing comment identified by {@code id}
     * within a specific schedule identified by {@code scheduleId}.
     * The update request requires a valid UpdateCommentRequestDto.
     *
     * @param id The ID of the comment to be updated.
     * @param scheduleId The ID of the schedule to which the comment belongs.
     * @param requestDto The data transfer object containing the updated comment content.
     * @param request The HTTP request object containing session and authentication details.
     * @return A ResponseEntity with HTTP status 200 upon successful update.
     * @throws com.example.schedule.exception.NoContentException If the id of the comment and the author doesn't exist.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateComments(
            @PathVariable Long id,
            @PathVariable Long scheduleId,
            @RequestBody UpdateCommentRequestDto requestDto,
            HttpServletRequest request
    ){
        commentService.updateComments(id, scheduleId, requestDto, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Deletes a specific comment associated with a schedule.
     *
     * This method removes a comment identified by {@code id} from the system.
     * The request must contain a valid session, and only authorized users can delete comments.
     *
     * @param id The ID of the comment to be deleted.
     * @param request The HTTP request containing session and authentication details.
     * @param scheduleId The ID of the schedule to which the comment belongs (incorrectly declared as String, should be Long).
     * @return A ResponseEntity with HTTP status 200 upon successful deletion.
     * @throws com.example.schedule.exception.NoContentException If the id of the comment and author doesn't exist.
     * @throws com.example.schedule.exception.ForbiddenException If the author tires to delete other's comment.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComments(
            @PathVariable Long id,
            HttpServletRequest request,
            @PathVariable String scheduleId){
        commentService.deleteComments(id, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
