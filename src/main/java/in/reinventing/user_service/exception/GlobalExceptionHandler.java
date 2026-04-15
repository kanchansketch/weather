package in.reinventing.user_service.exception;

import in.reinventing.user_service.dto.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse methodArgInvalidException(HttpServletRequest request, MethodArgumentNotValidException e){
        return APIResponse.builder()
                .fields(e.getFieldErrors().stream().map(f->Map.of("field",f.getRejectedValue(),"message",f.getDefaultMessage())).collect(Collectors.toList()))
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .error("Invalid input!")
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse userNotFound(HttpServletRequest request, UsernameNotFoundException e){
        return APIResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .error(e.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = {RestClientException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse restClientException(HttpServletRequest request, RestClientException e){
        return APIResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .error(e.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse internalServerError(HttpServletRequest request, Exception e){
        return APIResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .error(e.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }
}
