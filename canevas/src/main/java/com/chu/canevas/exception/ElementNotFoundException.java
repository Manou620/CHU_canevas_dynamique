package com.chu.canevas.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message){
        super(message);
    }

    public ElementNotFoundException(String type, String ID){
        super("l'element " + type + "avec l'identifiant: " + ID + " ne peut etre trouvé");
    }


}
