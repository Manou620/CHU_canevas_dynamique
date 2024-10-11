package com.chu.canevas.exception;

public class JwtTokenExpiredException extends JwtTokenException {
    public JwtTokenExpiredException() {
        super("Le token a expire");
    }
}
