package br.com.diogomendes.post.service.api.exceptionhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estão invalidos");
        problemDetail.setType(URI.create("https://diogomendes.com/erros/campos-invalidos"));

        var fields = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        objectError -> ((FieldError) objectError).getField(),
                        objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
                ));

        problemDetail.setProperty("fields", fields);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }
}
