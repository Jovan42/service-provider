package service.ordering.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import service.sharedlib.dto.ErrorMessage;
import service.sharedlib.exceptions.BadRequestException;

@EnableWebMvc
@ControllerAdvice
public class EventsExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> invalidOrderExceptionHandler(
            BadRequestException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }
}
