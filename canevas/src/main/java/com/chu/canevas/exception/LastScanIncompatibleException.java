package com.chu.canevas.exception;

import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Scan;
import com.chu.canevas.model.Sortie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LastScanIncompatibleException extends RuntimeException {

    private Scan LastScan;
    private Scan registeredScan;

    public LastScanIncompatibleException(Scan lastscan, Scan registeredScan ){
        super (messageBuilderException(lastscan));
        this.LastScan = lastscan;
        this.registeredScan = registeredScan;
    }
    public LastScanIncompatibleException(Scan lastscan){
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
