package com.shop.vegetable.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.shop.vegetable.exception.RecordNotFoundException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandlerController implements ErrorController {

    @ExceptionHandler(Exception.class)
    public String handleAllException(HttpServletRequest request, Exception ex, Model model) {

        ex.printStackTrace();
        model.addAttribute("status", 500);
        model.addAttribute("errorMessage", ex.getLocalizedMessage());
        // model.addAttribute("exclass", ex.getClass().getName());
        // model.addAttribute("root", ex.getCause());
        return "auth/error";
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public String exception(RecordNotFoundException exception, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("errorMessage", "Not Found");
        return "auth/error";
    }

}
