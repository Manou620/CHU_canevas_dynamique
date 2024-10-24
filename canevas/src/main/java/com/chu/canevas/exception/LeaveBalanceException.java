package com.chu.canevas.exception;

import com.chu.canevas.model.Personnel;

public class LeaveBalanceException extends RuntimeException{
    public LeaveBalanceException(Personnel personnel){
        super("Le personnel : " + "'" + personnel.getImmatriculation() + "," + personnel.getNom() + "'" + "excede sa limite d'absence pour la periode donnée");
    }
}
