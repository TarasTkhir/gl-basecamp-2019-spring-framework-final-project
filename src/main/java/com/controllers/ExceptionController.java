package com.controllers;

import com.exception.CompareAgeEntityNotFound;
import com.exception.NotComperableCharactersException;
import com.exception.PersonNotFoundException;
import com.wire.ExceptionResponseWire;
import com.wire.ValidatorResponseWire;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionResponseWire> notFoundException(PersonNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ExceptionResponseWire(HttpStatus.NOT_FOUND.toString(), ex.getMessage()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ValidatorResponseWire> handleResourceNotFoundException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ValidatorResponseWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(value = {NotComperableCharactersException.class})
    public ResponseEntity<ExceptionResponseWire> handleNotComparableCharactersException(NotComperableCharactersException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ExceptionResponseWire(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(value = {CompareAgeEntityNotFound.class})
    public ResponseEntity<ExceptionResponseWire> ageCompareEntityNotFoundException(CompareAgeEntityNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(new ExceptionResponseWire(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
    }
}
