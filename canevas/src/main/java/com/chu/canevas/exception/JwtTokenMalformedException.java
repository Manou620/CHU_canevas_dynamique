package com.chu.canevas.exception;

public class JwtTokenMalformedException extends JwtTokenException{
    public JwtTokenMalformedException() {
        super("Token Invalide");
    }
}
