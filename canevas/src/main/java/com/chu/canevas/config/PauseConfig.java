package com.chu.canevas.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalTime;


public class PauseConfig {

    @Value("${horaire.debut_pause}")
    private String debut_pause;
    @Value("${horaire.fin_pause}")
    private String fin_pause;

    public LocalTime getDebutPause() {
        return LocalTime.parse(debut_pause);
    }

    public LocalTime getFinPause() {
        return LocalTime.parse(fin_pause);
    }

}
