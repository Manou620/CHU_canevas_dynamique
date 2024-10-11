package com.chu.canevas.exception;

public class JwtTokenSignatureException extends JwtTokenException{
    public JwtTokenSignatureException() {
        super("Validation du signature echoue");
    }
}
