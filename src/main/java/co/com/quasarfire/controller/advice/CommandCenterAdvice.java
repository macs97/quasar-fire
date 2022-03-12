package co.com.quasarfire.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommandCenterAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<HttpStatus> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
