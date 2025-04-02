package com.example.schedule.service;

import com.example.schedule.config.PasswordEncoder;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.dto.SignUpResponseDto;
import com.example.schedule.dto.UpdateInfoRequestDto;
import com.example.schedule.entity.Author;
import com.example.schedule.exception.EmailVerificationException;
import com.example.schedule.exception.InvalidPasswordException;
import com.example.schedule.exception.NoContentException;
import com.example.schedule.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;


    public SignUpResponseDto signUp(String username, String password, String email, Integer age) {

        authorRepository.findByEmail(email)
                .ifPresent(author -> {
                    throw new EmailVerificationException("Email already in use");
                });

        password = passwordEncoder.encode(password);

        Author author = new Author(username, email, password, age);

        Author savedAuthor = authorRepository.save(author);

        return new SignUpResponseDto(savedAuthor.getId(), savedAuthor.getUsername(), savedAuthor.getEmail(), savedAuthor.getAge());
    }

    public AuthorResponseDto findById(long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if(optionalAuthor.isEmpty()){
            throw new NoContentException("Author Does Not Exist.");
        }

        Author foundAuthor = optionalAuthor.get();
        return new AuthorResponseDto(foundAuthor.getUsername(), foundAuthor.getAge(), foundAuthor.getEmail());
    }

    @Transactional
    public void updateInfo(Long id, UpdateInfoRequestDto requestDto) {

        Author foundAuthor = findByIdOrElseThrow(id);

        // 1️⃣ Authorizing
        if(!foundAuthor.getPassword().equals(requestDto.getOldPassword())){
            throw new InvalidPasswordException("Incorrect Password");
        }

        // 2️⃣ Updates Password if New Password is Typed.
        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isBlank()) {
            foundAuthor.setPassword(requestDto.getNewPassword());
        }

        // 3️⃣ Updates Email if New Email is Typed and Not Duplicated.
        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank() && !requestDto.getEmail().equals(foundAuthor.getEmail())) {
            boolean emailExists = authorRepository.existsByEmail(requestDto.getEmail());
            if (emailExists) {
                throw new EmailVerificationException("Email already in use");
            }
            foundAuthor.setEmail(requestDto.getEmail());
        }

        // 4️⃣ Updates Name if New Name is Typed.
        if (requestDto.getUsername() != null && !requestDto.getUsername().isBlank()) {
            foundAuthor.setUsername(requestDto.getUsername());
        }

        // 5️⃣ Updates Age if Age is Newly Typed.
        if (requestDto.getAge() != null) {
            foundAuthor.setAge(requestDto.getAge());
        }

    }


    public Author findByIdOrElseThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NO_CONTENT, "ID Does Not Exist. ID = " + id
                ));
    }

    public LoginResponseDto login(String email, String password) {
        Author author = authorRepository.findIdByEmail(email);

        if(author == null){
            throw new InvalidPasswordException("Incorrect Email");
        }


        if(!passwordEncoder.matches(password, author.getPassword())){
            throw new InvalidPasswordException("Incorrect Password");
        }

        return new LoginResponseDto(author.getId());
    }
}
