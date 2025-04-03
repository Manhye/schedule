package com.example.schedule.filter;

import com.example.schedule.exception.ForbiddenException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/authors/signup", "/login", "/logout","/favicon.ico","/home"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("login filter proceed");


        if (!isWhiteList(requestURI)) {
            log.info("Checking whitelist for URI: " + requestURI);


            HttpSession session = httpRequest.getSession(false);


            if (session == null || session.getAttribute("loginAuthor") == null) {
                throw new ForbiddenException("login please.");
            }

            log.info("login success");

        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {

        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}