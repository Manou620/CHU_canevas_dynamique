package com.chu.canevas.utils;

import com.chu.canevas.enums.TypeAbsenceFrequencyEnum;
import com.chu.canevas.model.Absence;
import com.chu.canevas.model.Personnel;
import com.chu.canevas.model.Type_absence;
import com.chu.canevas.repository.AbsenceRepository;
import com.chu.canevas.repository.NonWorkingDaysRepository;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.List;

public class AbsenceUtils {

    public static boolean canTakeAbsenceIndvidually(
            Personnel personnel,
            Type_absence typeAbsence,
            LocalDateTime debutDateDemande,
            LocalDateTime finDateDemande,
            AbsenceRepository absenceRepository,
            NonWorkingDaysRepository nonWorkingDaysRepository
    ){

        LocalDate startOfDemandeAbsence = debutDateDemande.toLocalDate();
        LocalDateTime currentStart = debutDateDemande;

        if(typeAbsence.getNomTypeConge().equals("MISSION")){
            return true;
        }



        // Loop through each frequency period until the holiday ends
        while (!currentStart.isAfter(finDateDemande)){
            LocalDateTime debutPeriode = getStartOfFrequencyPeriod(typeAbsence.getFrequence(), currentStart);
            LocalDateTime finPeriode = getEndOfFrequencyPeriod(typeAbsence.getFrequence(), currentStart);

//            if (finPeriode == null || debutPeriode == null) {
//                return true;
//            }

            // Adjust period end to ensure it does not exceed the holiday end date
            LocalDateTime effectivePeriodEnd = finPeriode.isAfter(finDateDemande)? finDateDemande : finPeriode;

            //Calculate how many days of the holiday fall within this period
            //Mila esorina ny jours ferriers (eto kay no tena ilaina ny operation ao am calculateAbsenceDurationExcludingWeekendsAndHolidays )
            //Lasa 0 eto reh tsa ampy 24h
            Long dureeAbsencePourAttribuerDansLaPeriode = 0L;

            while (!startOfDemandeAbsence.isAfter(effectivePeriodEnd.toLocalDate())){
                DayOfWeek dayOfWeek = startOfDemandeAbsence.getDayOfWeek();
                if(!nonWorkingDaysRepository.existsByDate(startOfDemandeAbsence) || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                    dureeAbsencePourAttribuerDansLaPeriode++;
                }
                startOfDemandeAbsence = startOfDemandeAbsence.plusDays(1);
            }


            //Get the total absence for this period
            //Lasa ngeza ny valiny satria mcalcul my jours de travail mandritra ny periode raika manontolo.
            Long totalAbsenceInPeriod = calculateAbsenceDurationExcludingWeekendsAndHolidays(
                personnel, typeAbsence, debutPeriode, finPeriode, absenceRepository, nonWorkingDaysRepository
            );

            // Check if the sum of absences in this period exceeds the allowed cumulative duration
            //absence tam DB + Absence vo hampidirina (efa samy tsisy jours ferriers aby)
            if(totalAbsenceInPeriod + dureeAbsencePourAttribuerDansLaPeriode > typeAbsence.getDuree_max_cumul_autorise()){
                return false;
            }

            // Move to the next period (start at the day after the current period end)
            currentStart = finPeriode.plusDays(1);
        }

        // The holiday is allowed for all periods
        return true;
    }

    public static LocalDateTime getStartOfFrequencyPeriod(TypeAbsenceFrequencyEnum frequence, LocalDateTime date){
        switch (frequence){
            case ANNUEL -> {
                return LocalDateTime.of(date.getYear(), 1, 1, 0, 0);
            }
            case TRIMESTRIEL -> {
                return getStartOfQuarter(date);
            }
            case MENSUEL -> {
                return getStartOfMonth(date);
            }
            case HEBDOMADAIRE -> {
                return getStartOfTheWeek(date);
            }
            case JOURNALIER -> {
                return getStartOfDay(date);
            }
            case null, default -> {
                return null;
            }
        }
    }

    public static LocalDateTime getEndOfFrequencyPeriod(TypeAbsenceFrequencyEnum frequence, LocalDateTime date){
        switch (frequence){
            case ANNUEL -> {
                return LocalDateTime.of(date.getYear(), 12, 31, 23, 59);
            }
            case TRIMESTRIEL -> {
                return getEndOfQuarter(date);
            }
            case MENSUEL -> {
                return getEndOfMonth(date);
            }
            case HEBDOMADAIRE -> {
                return getEndOfTheWeek(date);
            }
            case JOURNALIER -> {
                return getEndOfTheDay(date);
            }
            case null, default -> {
                return null;
            }
        }
    }

    //getStart of Trimestre
    public static LocalDateTime getStartOfQuarter(LocalDateTime date){
        int currentMonth = date.getMonthValue();
        int startMonth;
        if(currentMonth <= 3){
            startMonth = 1; //Janvier
        } else if (currentMonth <= 6) {
            startMonth = 4;
        }else if (currentMonth <= 9) {
            startMonth = 7;
        }else {
            startMonth = 10;
        }
        return  LocalDateTime.of(date.getYear(), startMonth, 1, 0, 0);
    }

    //getEnd of Trimestre
    public static LocalDateTime getEndOfQuarter(LocalDateTime date){
        int currentMonth = date.getMonthValue();
        int endMonth;
        if(currentMonth <= 3){
            endMonth = 3; //Janvier
        } else if (currentMonth <= 6) {
            endMonth = 6;
        }else if (currentMonth <= 9) {
            endMonth = 9;
        }else {
            endMonth = 12;
        }
        LocalDate enDate = YearMonth.of(date.getYear(), endMonth).atEndOfMonth();
        return  LocalDateTime.of(enDate, LocalTime.of(23, 59));
    }

    // ######### Hebdomadaire #################
    public static LocalDateTime getStartOfTheWeek(LocalDateTime date){
        return date.with(ChronoField.DAY_OF_WEEK, 7).with(LocalTime.MIN);
    }

    public static LocalDateTime getEndOfTheWeek(LocalDateTime date){
        return date.with(ChronoField.DAY_OF_WEEK, 1).with(LocalTime.MAX);
    }

    // ########## Journalier #############3
    public static LocalDateTime getStartOfDay(LocalDateTime date){
        return date.with(LocalTime.MIN);
    }

    public static LocalDateTime getEndOfTheDay(LocalDateTime date){
        return date.with(LocalTime.MAX);
    }

    //########### MENSUEL ###############
    public static LocalDateTime getStartOfMonth(LocalDateTime date){
        return date.withDayOfMonth(1).with(LocalTime.MIN);
    }

    public static LocalDateTime getEndOfMonth(LocalDateTime date){
        return date.withDayOfMonth(date.toLocalDate().lengthOfMonth()).with(LocalTime.MAX);
    }

    //CALULER la somme des absences pendant une periode
    public static long calculateAbsenceDurationExcludingWeekendsAndHolidays(
            Personnel personnel, Type_absence typeAbsence, LocalDateTime debutPeriod, LocalDateTime finPeriod,
            AbsenceRepository absenceRepository, NonWorkingDaysRepository nonWorkingDaysRepository
    ){
        //Jours d'absence any am DB no haza eto
        //Lasa tsa miasa koa sehteo nefa mila jerena ny jours ferrier ao
        //Tena mila jerena ary am repository fa ny SQL mhitsy no mila ovaina hanalaina ny jours ferriers tao
            //Mety hoe Listen'ny conge am periode no haza de atao boucle avy eo hanalina ny jours ferrries tao
        //Ny demande d'absence vo hatao no mila an'le calcul lava de eo ambany hahalalana ny jours ferriers mba hahalalana ny isan'ny jours mila kotina

//        Long totalAbsenceDays = absenceRepository.sumAbsenceDurationWithinPeriod(
//                personnel.getImmatriculation(), typeAbsence.getId_type_absence(), debutPeriod, finPeriod);

        List<Absence> ListAbsenceForThePeriod = absenceRepository.absenceListWithinPeriod(
                personnel.getImmatriculation(), typeAbsence.getId_type_absence(), debutPeriod, finPeriod
        );

        if(ListAbsenceForThePeriod == null || ListAbsenceForThePeriod.isEmpty()){
            return 0L;
        }

        long totalAbsenceOfPeriod = 0;

        for (Absence absence : ListAbsenceForThePeriod){
            LocalDate startDate = absence.getDebut_absence().toLocalDate();
            LocalDate endDate = absence.getFin_absence().toLocalDate();

            // Iterate over each day in the absence period
            //sod kotina koa ny egal /// Tokony heure no kotina fa tsa precis lotra
            while (!startDate.isAfter(endDate)){
                DayOfWeek dayOfWeek = startDate.getDayOfWeek();
                if(!nonWorkingDaysRepository.existsByDate(startDate) || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                    totalAbsenceOfPeriod++;
                }
                startDate = startDate.plusDays(1);
            }
        }

        return totalAbsenceOfPeriod;

    }



}
