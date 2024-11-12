package com.chu.canevas.exception;

public class SamePersonAsChefException extends RuntimeException {
    public SamePersonAsChefException() {
        super("Une personne ne peut peut etre son propre chef");
    }
}
