package com.example.schedule.service;

import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public SignUpResponseDto signUp(String username, String password, String email, Integer age) {
        Author author = new Author(username, password, email, age);

        Author savedAuthor = authorRepository.save(author);

        return new SignUpResponseDto(savedAuthor.getId(), savedAuthor.getUsername(), savedAuthor.getEmail(), savedAuthor.getAge());
    }
}
