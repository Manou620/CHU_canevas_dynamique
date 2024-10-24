package com.chu.canevas.exception;

import com.chu.canevas.model.Personnel;

public class InterimNotAvailableException extends RuntimeException{
    public InterimNotAvailableException(String message){
        super(message);
    }

    public InterimNotAvailableException(Personnel interim){
        super("L'interim selectionné : " + interim.getNom() + " avec l'immatriculation : " + interim.getImmatriculation() + " n'est pas disponible pour la periode");
    }
}
