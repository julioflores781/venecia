package com.test.venecia.exception;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SpaceshipNotFoundException.class)
    public ResponseEntity<String> handleSpaceshipNotFoundException(SpaceshipNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(KafkaSendFailedException.class)
    public ResponseEntity<String> handleKafkaSendFailedException(KafkaSendFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(CustomUsernameNotFoundException.class)
    public ResponseEntity<String> handleCustomUsernameNotFoundException(CustomUsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autorizacion: " + ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleCustomExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Expirado: " + ex.getMessage());
    }
    @ExceptionHandler(SQLServerException.class)
    public ResponseEntity<String> handleSQLServerException(SQLServerException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error en la base de datos: " + ex.getMessage());
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error con el jwt malformado: " + ex.getMessage());
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error con el jwt: " + ex.getMessage());
    }
}
