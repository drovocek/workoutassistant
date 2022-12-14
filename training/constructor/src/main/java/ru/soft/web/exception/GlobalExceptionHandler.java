package ru.soft.web.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.soft.utils.ValidationUtil;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ErrorAttributes errorAttributes;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> PSQLException(WebRequest request, PSQLException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        return createResponseEntity(request, ErrorAttributeOptions.of(MESSAGE), "DB constraints exception", HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appException(WebRequest request, AppException ex) {
        log.error("ApplicationException: {}", ex.getMessage());
        return createResponseEntity(request, ex.getOptions(), null, ex.getStatusCode());
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        log.error("Exception", ex);
        super.handleExceptionInternal(ex, body, headers, statusCode, request);
        return createResponseEntity(request, ErrorAttributeOptions.of(), ValidationUtil.getRootCause(ex).getMessage(), statusCode);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        return handleBindingErrors(ex.getBindingResult(), request);
    }

    private ResponseEntity<Object> handleBindingErrors(BindingResult result, WebRequest request) {
        String msg = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return createResponseEntity(request, ErrorAttributeOptions.defaults(), msg, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @SuppressWarnings("unchecked")
    protected <T> ResponseEntity<T> createResponseEntity(WebRequest request, ErrorAttributeOptions options, String msg, HttpStatusCode statusCode) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        if (msg != null) {
            body.put("message", msg);
        }
        body.put("status", statusCode.value());
        if (statusCode instanceof HttpStatus status) {
            body.put("error", status.getReasonPhrase());
        }
        return (ResponseEntity<T>) ResponseEntity.status(statusCode).body(body);
    }
}
