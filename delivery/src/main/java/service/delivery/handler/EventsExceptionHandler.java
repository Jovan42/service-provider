package service.delivery.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import service.sharedlib.dto.ErrorMessage;
import service.sharedlib.exceptions.BadRequestException;
import service.sharedlib.exceptions.NotFoundException;

@EnableWebMvc
@ControllerAdvice
public class EventsExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> invalidBadRequestExceptionHandler(
            BadRequestException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> invalidNotFoundExceptionHandler(
            NotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage(), 404), HttpStatus.NOT_FOUND);
    }
}
