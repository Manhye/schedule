package com.example.schedule.controller;


import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.SignUpRequestDto;
import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.dto.UpdateInfoRequestDto;
import com.example.schedule.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;


    /**
     * Handles user sign-up requests.
     *
     * This method registers a new author with the provided username, password, email, and age.
     * The user information is validated by email before being processed.
     *
     * @param requestDto The DTO containing the sign-up details (username, password, email, age).
     * @return ResponseEntity containing a {@link SignUpResponseDto} with user details upon successful registration.
     * @throws com.example.schedule.exception.GlobalExceptionHandler If request validation fails.
     * @throws com.example.schedule.exception.EmailVerificationException If email already exists.
     */
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

    /**
     * Retrieves an author's details by their ID.
     *
     * This method gets the author information corresponding to the given ID.
     * If the author exists, their details are returned as a response.
     *
     * @param id The unique identifier of the author.
     * @return ResponseEntity containing an {@link AuthorResponseDto} with the author's details.
     * @throws com.example.schedule.exception.NoContentException If the author with the given ID doesn't exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> findById(@PathVariable long id) {
        AuthorResponseDto authorResponseDto = authorService.findById(id);
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    /**
     * Updates the information of an existing author.
     *
     * This method updates the author's details such as username, email, or other relevant fields
     * based on the provided {@link UpdateInfoRequestDto}. The update is applied to the author
     * identified by the given ID.
     *
     * @param id The unique identifier of the author to be updated.
     * @param requestDto The {@link UpdateInfoRequestDto} containing the updated author details.
     * @return {@link ResponseEntity} with HTTP status 200 (OK) if the update is successful.
     * @throws com.example.schedule.exception.InvalidPasswordException If password is wrong.
     * @throws com.example.schedule.exception.EmailVerificationException If duplicated email is written.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateInfo(
            @PathVariable Long id,
            @RequestBody @Valid UpdateInfoRequestDto requestDto
    ){
        authorService.updateInfo(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
