package it.uniroma3.siw.siwfood.exceptions;

import org.springframework.http.HttpStatus;

public class authException extends RuntimeException {

    public final HttpStatus httpStatus;

    public authException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
