package com.example.schedule.service;

import com.example.schedule.common.Const;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.CommentRequestDto;
import com.example.schedule.dto.CommentResponseDto;
import com.example.schedule.dto.UpdateCommentRequestDto;
import com.example.schedule.entity.Author;
import com.example.schedule.entity.Comment;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.NoContentException;
import com.example.schedule.repository.AuthorRepository;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;


    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, String email) {

        Optional<Author> author = authorRepository.findByEmail(email);

        if(author.isEmpty()){
            throw new NoContentException("Author Does Not Exist.");
        }

        Author foundAuthor = author.get();

        Optional<Schedule> foundSchedule = scheduleRepository.findById(id);

        if(foundSchedule.isEmpty()){
            throw new NoContentException("Schedule Does Not Exist.");
        }
        Schedule schedule = foundSchedule.get();

        Comment comment = new Comment(requestDto.getComments());
        comment.setAuthor(foundAuthor);
        comment.setSchedule(schedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getComments(),
                savedComment.getSchedule().getId(),
                savedComment.getAuthor().getEmail()
        );
    }

    public List<CommentResponseDto> getComments(Long id) {
        return commentRepository.findByScheduleId(id).stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getComments(),
                        comment.getSchedule().getId(),
                        comment.getAuthor().getEmail()
                )).toList();
    }

    @Transactional
    public void updateComments(Long id, Long scheduleId, UpdateCommentRequestDto requestDto, HttpServletRequest request) {

        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new NoContentException("Comment Does Not Exist. ID = " + id));

        Author authorOfComment = authorRepository.findById(foundComment.getAuthor().getId())
                .orElseThrow(() -> new NoContentException("Author Does Not Exist."));

        HttpSession session = request.getSession();
        Object dto = session.getAttribute(Const.LOGIN_AUTHOR);

        String email = null;
        if(dto instanceof AuthorResponseDto){
            email = ((AuthorResponseDto) dto).getEmail();
        }

        if(!authorOfComment.getEmail().equals(email)){
            throw new NoContentException("You are not authorized to update this comment.");
        }

        foundComment.setComments(requestDto.getComments());

    }

    public void deleteComments(Long id, Long scheduleId, UpdateCommentRequestDto requestDto, HttpServletRequest request) {
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new NoContentException("Comment Does Not Exist. ID = " + id));
        Author authorOfComment = authorRepository.findById(foundComment.getAuthor().getId())
                .orElseThrow(() -> new NoContentException("Author Does Not Exist."));

        HttpSession session = request.getSession();
        Object dto = session.getAttribute(Const.LOGIN_AUTHOR);

        String email = null;
        if(dto instanceof AuthorResponseDto){
            email = ((AuthorResponseDto) dto).getEmail();
        }

        if(!authorOfComment.getEmail().equals(email)){
            throw new NoContentException("You are not authorized to delete this comment.");
        }

        commentRepository.delete(foundComment);

    }
}
