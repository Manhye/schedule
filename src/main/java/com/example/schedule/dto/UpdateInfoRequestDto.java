package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UpdateInfoRequestDto {

    @NotNull(message = "Existing Password is Required")
    private final String oldPassword;

    private final String newPassword;

    @Email(message = "Not an Email format")
    private final String email;

    private final String username;

    @Min(value = 8, message = "Age must be at least 8")
    @Max(value = 120, message = "Age must be at most 120")
    private final Integer age;
}
