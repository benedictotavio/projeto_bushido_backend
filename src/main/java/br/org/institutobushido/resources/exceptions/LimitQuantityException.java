package br.org.institutobushido.resources.exceptions;

public class LimitQuantityException extends RuntimeException {
    public LimitQuantityException(String message) {
        super(message);
    }
}
