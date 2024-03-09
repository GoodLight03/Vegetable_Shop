package com.shop.vegetable.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandlerController implements ErrorController {

    @ExceptionHandler(Exception.class)
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllException(HttpServletRequest request,Exception ex, Model model) {

        // Object status = request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE);

        // if (status != null) {
        //     int statusCode = Integer.parseInt(status.toString());

        //     if (statusCode == HttpStatus.NOT_FOUND.value()) {
        //         model.addAttribute("status", 404);
        //         model.addAttribute("errorMessage", "Lỗi ???");
        //         return "auth/error"; // Chuyển hướng đến trang view mặc định cho lỗi 404
        //     }
        // }

        ex.printStackTrace();
        model.addAttribute("status", 500);
        model.addAttribute("errorMessage", ex.getLocalizedMessage());
        // model.addAttribute("exclass", ex.getClass().getName());
        // model.addAttribute("root", ex.getCause());
        return "auth/error";
    }

    // @ExceptionHandler(NoHandlerFoundException.class)
    // public String handle404Error() {
    //     return "error/404"; // Chuyển hướng đến trang view mặc định cho lỗi 404
    // }
    

    // @ExceptionHandler(NotFoundException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public String handleNotFoundException(NotFoundException  ex, Model model) {
    //     ex.printStackTrace();
    //     model.addAttribute("errorMessage", ex.getLocalizedMessage());
    //     return "auth/error";
    // }

    // @ExceptionHandler(AccessDeniedException.class)
    // @ResponseStatus(HttpStatus.FORBIDDEN)
    // public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
    //     ex.printStackTrace();
    //     model.addAttribute("errorMessage", ex.getLocalizedMessage());
    //     return "auth/error";
    // }

}
