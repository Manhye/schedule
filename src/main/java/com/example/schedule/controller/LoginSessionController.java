package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.config.PasswordEncoder;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginSessionController {

    private final AuthorService authorService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/home")
    public String login(
            HttpServletRequest request,
            Model model
    ){

        HttpSession session = request.getSession(false);

        if(session == null){
            return "login";
        }

        AuthorResponseDto loginAuthor = (AuthorResponseDto) session.getAttribute(Const.LOGIN_AUTHOR);


        if(loginAuthor == null){
            return "login";
        }

        model.addAttribute("loginAuthor", loginAuthor);
        return "home";
    }


    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginRequestDto dto,
            HttpServletRequest request
    ) {

        LoginResponseDto responseDto = authorService.login(dto.getEmail(), dto.getPassword());
        Long userId = responseDto.getId();

        if (userId == null) {
            return "/login";
        }

        HttpSession session = request.getSession();

        AuthorResponseDto loginAuthor = authorService.findById(userId);

        session.setAttribute(Const.LOGIN_AUTHOR, loginAuthor);

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/home";
    }
}
