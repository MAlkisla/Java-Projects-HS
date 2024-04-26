package cinema.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CinemaExceptionHandler {

    @ExceptionHandler(SeatNotAvailableException.class)
    public ResponseEntity<String> handleSeatNotAvailable(SeatNotAvailableException ex) {
        return ResponseEntity
                .badRequest()
                .body("{\"error\": \"The ticket has been already purchased!\"}");
    }

    @ExceptionHandler(SeatLocationOutOfBoundsException.class)
    public ResponseEntity<String> handleSeatLocationOutOfBounds(SeatLocationOutOfBoundsException ex) {
        return ResponseEntity
                .badRequest()
                .body("{\"error\": \"The number of a row or a column is out of bounds!\"}");
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<String> handleWrongToken(WrongTokenException ex) {
        return ResponseEntity
                .badRequest()
                .body("{\"error\": \"Wrong token!\"}");
    }

}