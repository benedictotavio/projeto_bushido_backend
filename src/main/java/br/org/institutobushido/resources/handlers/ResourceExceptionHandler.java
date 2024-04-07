package br.org.institutobushido.resources.handlers;

import java.time.Instant;
import java.util.Map;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import br.org.institutobushido.controllers.response.error.StandardError;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.resources.exceptions.InactiveUserException;
import br.org.institutobushido.resources.exceptions.LimitQuantityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ResourceExceptionHandler {
    ProblemDetail problem;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(EntityNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Object Not Found");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<StandardError> objectIsAlreadyRegistered(AlreadyRegisteredException e,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.CONFLICT.value());
        err.setError("Object is already Registered");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(LimitQuantityException.class)
    public ResponseEntity<StandardError> objectHasNoQuantity(LimitQuantityException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        err.setError("Object has no quantity limit");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<StandardError> inactiveUser(InactiveUserException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        err.setError("Object is inactive");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> invalidProperty(MethodArgumentNotValidException e,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        err.setError("Invalid Property");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> invalidProperty(ValidationException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.FORBIDDEN.value());
        err.setError("Invalid Attribute");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(NestedRuntimeException.class)
    public ResponseEntity<StandardError> invalidProperty(NestedRuntimeException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.FORBIDDEN.value());
        err.setError("Object not support");
        err.setMessage(e.getLocalizedMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception e) {

        if (e instanceof BadCredentialsException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            problem.setTitle("Erro de Autenticação");
            problem.setProperties(
                    Map.of("error:", "Credenciais Inválidas", "message:", "E-mail e senha não conferem."));
        }

        if (e instanceof AccessDeniedException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            problem.setTitle("Access Denied Error");
            problem.setProperty("access_denied", "Voce não esta autorizado a acessar essa sessão.");
        }

        if (e instanceof TokenExpiredException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            problem.setTitle("JWT Expired");
            problem.setProperty("jwt_error", "JWT Token esta expirado");
        }

        if (e instanceof SignatureVerificationException) {
            problem = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            problem.setTitle("JWT Signature error");
            problem.setProperty("jwt_error", "JWT Token não está no formato correto");
        }
        return problem;
    }
}
