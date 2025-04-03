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

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto requestDto,
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ){


        CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto, request);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId){
        List<CommentResponseDto> commentResponseDtoList = commentService.getComments(scheduleId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComments(
            @PathVariable Long id,
            @PathVariable Long scheduleId,
            @RequestBody UpdateCommentRequestDto requestDto,
            HttpServletRequest request
    ){
        commentService.deleteComments(id, scheduleId, requestDto, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
