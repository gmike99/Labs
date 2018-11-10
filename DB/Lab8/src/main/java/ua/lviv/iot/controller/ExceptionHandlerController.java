package ua.lviv.iot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.lviv.iot.DTO.MessageDTO;
import ua.lviv.iot.exceptions.NoSuchPlayerException;

public class ExceptionHandlerController {

    @ExceptionHandler(NoSuchPlayerException.class)
    ResponseEntity<MessageDTO> handleNoSuchPlayerException() {
        return new ResponseEntity<>(new MessageDTO("Such Player Not Found"), HttpStatus.NOT_FOUND);
    }
}
