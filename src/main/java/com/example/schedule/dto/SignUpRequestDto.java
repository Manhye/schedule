package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "Username is required")
    private final String username;

    @NotBlank(message = "Password is required")
    private final String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Not an email format")
    private final String email;

    @Min(value = 8, message = "Age must be at least 8")
    @Max(value = 120, message = "Age must be at most 120")
    private final Integer age;



}
