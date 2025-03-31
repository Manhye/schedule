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
     * @param requestDto
     * @return
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
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> findById(@PathVariable long id){
        AuthorResponseDto authorResponseDto = authorService.findById(id);

        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }

    /**
     * @param id
     * @param requestDto
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateInfoRequestDto> updateInfo(
            @PathVariable @Valid Long id,
            @RequestBody UpdateInfoRequestDto requestDto
    ){
        authorService.updateInfo(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
