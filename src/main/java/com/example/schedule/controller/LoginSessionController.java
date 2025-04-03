package com.example.schedule.controller;

import com.example.schedule.common.Const;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.LoginRequestDto;
import com.example.schedule.dto.LoginResponseDto;
import com.example.schedule.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginSessionController {

    private final AuthorService authorService;


    /**
     * Handles user login and redirects to the appropriate view.
     *
     * This method checks if a user session exists. If no session is found or if the user is not logged in,
     * it redirects to the login page. Otherwise, it retrieves the logged-in user's information
     * and loads the home page.
     *
     * @param request The HTTP request containing session information.
     * @param model The model to store user data for rendering the home page.
     * @return The name of the view to be rendered ("login" if authentication fails, "home" otherwise).
     */
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


    /**
     * Handles user login and redirects to the home page upon successful authentication.
     *
     * This method verifies the user's email and password. If authentication fails,
     * it redirects back to the login page. Upon success, it creates a new session
     * and stores the user's information before redirecting to the home page.
     *
     * @param dto The login request containing the user's email and password.
     * @param request The HTTP request used to create a new session.
     * @return A redirect string to either the login page (if authentication fails)
     *         or the home page (if authentication is successful).
     */
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


    /**
     * Logs out the currently authenticated user and invalidates the session.
     *
     * This method checks if an active session exists. If it does, the session is invalidated,
     * effectively logging out the user. After logging out, the user is redirected to the home page.
     *
     * @param request The HTTP request containing the current session.
     * @return A redirect string to the home page after logout.
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/home";
    }
}
