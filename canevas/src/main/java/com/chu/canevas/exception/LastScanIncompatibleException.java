package com.chu.canevas.exception;

import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Scan;
import com.chu.canevas.model.Sortie;

public class LastScanIncompatibleException extends RuntimeException {

    public LastScanIncompatibleException(Scan lastscan ){
        super (messageBuilderException(lastscan));
    }

    private static String messageBuilderException(Scan lastscan){
        if(lastscan instanceof Entry){
            return "Le dernier enregistrement pour l'employé : "
                    + lastscan.getPersonnel().getNom() + " est de type : 'Entrée'. "
                    + "L'entré sera enregistré mais confirmez la sortie correspondante au precedent entrée "
                    + "aupres du responsable ";
        }else if (lastscan instanceof Sortie){
            return "Le dernier enregistrement pour l'employé : "
                    + lastscan.getPersonnel().getNom() + " est de type : 'Sortie'. "
                    + "La sortie sera enregistré mais confirmez l'entrée correspondante au precedente sortie "
                    + "aupres du responsable ";
        }else {
            return "L'enregistrement du scam a échoué";
        }
    }

}
