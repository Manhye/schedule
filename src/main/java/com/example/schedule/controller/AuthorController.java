package com.example.schedule.controller;


import com.example.schedule.dto.SignUpRequestDto;
import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestDto){
        SignUpResponseDto signupResponseDto =
                authorService.signUp(
                        requestDto.getUsername(),
                        requestDto.getPassword(),
                        requestDto.getEmail(),
                        requestDto.getAge()
                );

        return new ResponseEntity<>(signupResponseDto, HttpStatus.OK);
    }
}
