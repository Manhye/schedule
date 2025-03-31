package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {
    private final String username;
    private final Integer age;
    private final String email;

}
