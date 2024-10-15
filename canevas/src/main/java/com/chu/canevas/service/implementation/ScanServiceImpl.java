package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.dto.dtoMapper.EntryDtoMapper;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.exception.LastScanIncompatibleException;
import com.chu.canevas.model.*;
import com.chu.canevas.repository.*;
import com.chu.canevas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class ScanServiceImpl  implements ScanService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private SortieRepository sortieRepository;

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private EntryDtoMapper entryDtoMapper;

    /**
     * @param personnel_IM
     * @return
     */
    @Override
    public Scan getLastScanRegistered(Personnel personnel) {
        Scan lastScan = entryRepository.findTopByPersonnelOrderByDateEnregistrementDesc(personnel);
        if(entryRepository.findTopByPersonnelOrderByDateEnregistrementDesc(personnel) != null) {
            return lastScan;
        }else{
            return null;
            //throw new ElementNotFoundException("Pas d'enregistrement trouvé");
        }
    }

    @Override
    public EntryDTO registerEntry(String personnel_IM, Long user_id){

            Instant scanMoment = Instant.now();
            ZonedDateTime zonedDateTime = scanMoment.atZone(ZoneId.systemDefault());
            LocalDate currentDate = zonedDateTime.toLocalDate();
            LocalTime currentTime = zonedDateTime.toLocalTime();

            //Verifiena hoe misy ve ny employee
            Personnel personnel = personnelRepository.findById(personnel_IM).orElseThrow(
                    () -> new ElementNotFoundException("Personnel", personnel_IM)
            );

            //Last entry
            Scan lastscan = getLastScanRegistered(personnel);

            //afaka atao anaty condition
            Utilisateur user_scanneur = new Utilisateur();
            user_scanneur.setId(user_id);

            //#########Conge & autorisé à s'absenter sa tsia

            // Mila horairen'ny employe
            Horaire horaire = personnel.getHoraire();

            /// ###### Hijerena ho is_Late sa tsia
            //remontena ny planning sy horaire
            Planning currentplanning = planningRepository.getCurrentPlanningOfAPersonnel(personnel_IM, LocalDate.now(), LocalTime.now());
            //Jerena reh first entree
            Boolean isFirstEntry = entryRepository.findEntryOfADateForAPersonnel(personnel_IM, LocalDate.now());

            //TAO ANATY TRY
            Entry entry = new Entry();
            entry.setPersonnel(personnel);
            //entry.getDate_enregistrement();


            //Checking for Late or Not
            if(currentplanning != null){
                if(currentplanning.getDebut_heure().isBefore(currentTime) || currentplanning.getDebut_heure().equals(currentTime)){
                    entry.setIs_late(true);
                }
            }else{
                if(!horaire.getFlexible() && (horaire.getDebut_horaire().isBefore(currentTime) || horaire.getDebut_horaire().equals(currentTime))){
                    entry.setIs_late(true);
                }
            }

            //First Entry ou pas
            if (isFirstEntry){
                entry.setFirst_entry(true);
            }

            //Associer avec un utilisateur
            entry.setUtilisateur(user_scanneur);

            entry.setType_horaire_attendu(personnel.getHoraire().getId_horaire());

            entry.setAnswer_sortie(null);

//            System.out.println("Sortie no farany tao");
//            System.out.println((Sortie) lastscan);
//            System.out.println("ENtree a enregistrer : ");
//            System.out.println(entry);
            //FIN TAO ANATY TRY

            Entry savedEntry = entryRepository.save(entry);
            if(lastscan instanceof Entry){
                System.out.println("entree");
                System.out.println((Entry) lastscan);
                throw new LastScanIncompatibleException(lastscan, savedEntry);
//                throw new LastScanIncompatibleException(lastscan);
            }
            return entryDtoMapper.apply(savedEntry);

    }

    //############# enregistrer une sortie
    //mila fantarina ko ve ny horaire sa de efa last Entry fotsiny no valina
    @Override
    public SortieDTO registerSortie(String personnel_IM, Long user_id) {
        Instant scanMoment = Instant.now();
        ZonedDateTime zonedDateTime = scanMoment.atZone(ZoneId.systemDefault());
        LocalDate currentDate = zonedDateTime.toLocalDate();
        LocalTime currentTime = zonedDateTime.toLocalTime();

        //Verifiena hoe misy ve ny employee
        Personnel personnel = personnelRepository.findById(personnel_IM).orElseThrow(
                () -> new ElementNotFoundException("Personnel", personnel_IM)
        );

        //Jerena ny last Scan
        Scan lastscan = getLastScanRegistered(personnel);

        //afaka atao anaty condition
        Utilisateur user_scanneur = new Utilisateur();
        user_scanneur.setId(user_id);

        //#########Conge & autorisé à s'absenter sa tsia

        // Mila horairen'ny employe ve?
//        Horaire horaire = personnel.getHoraire();
        return null;
    }
}
