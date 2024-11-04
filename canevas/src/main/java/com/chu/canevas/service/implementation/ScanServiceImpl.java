package com.chu.canevas.service.implementation;

import com.chu.canevas.config.PauseConfig;
import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.SortieDTO;
import com.chu.canevas.dto.dtoMapper.EntryDtoMapper;
import com.chu.canevas.dto.dtoMapper.PresentPersonnelDtoMapper;
import com.chu.canevas.dto.dtoMapper.SortieDtoMapper;
import com.chu.canevas.exception.ElementDuplicationException;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.exception.LastScanIncompatibleException;
import com.chu.canevas.exception.NoCompatibleEntryRegisterdException;
import com.chu.canevas.model.*;
import com.chu.canevas.repository.*;
import com.chu.canevas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SortieDtoMapper sortieDtoMapper;

    @Autowired
    private PresentPersonnelDtoMapper presentPersonnelDtoMapper;

//    @Autowired
//    private PauseConfig pauseConfig;



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
            LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();

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
            Planning currentplanning = planningRepository.getCurrentPlanningOfAPersonnel(personnel_IM, LocalDate.now(), LocalDateTime.now());
            //Jerena reh first entree
            Boolean isFirstEntry = entryRepository.findEntryOfADateForAPersonnel(personnel_IM, LocalDate.now());

            //TAO ANATY TRY
            Entry entry = new Entry();
            entry.setPersonnel(personnel);
            //entry.getDate_enregistrement();

            //Checking for Late or Not
            if(currentplanning != null){
                if(currentplanning.getDebut_heure().isBefore(currentDateTime) || currentplanning.getDebut_heure().equals(currentDateTime)){
                    entry.setIs_late(true);
                }
            }else{
                if(!horaire.getFlexible() && (horaire.getDebut_horaire().isBefore(currentTime) || horaire.getDebut_horaire().equals(currentDateTime))){
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
        LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();

        LocalTime debut_pause = LocalTime.parse("12:00:00");
        LocalTime fin_pause = LocalTime.parse("13:30:00");

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
        Horaire horaire = personnel.getHoraire();

        /// ###### Hijerena ho is_Late sa tsia

        //remontena ny planning sy horaire
        Planning currentplanning = planningRepository.getCurrentPlanningOfAPersonnel(personnel_IM, LocalDate.now(), LocalDateTime.now());
        //Jerena reh first entree
        Boolean isFirstEntry = entryRepository.findEntryOfADateForAPersonnel(personnel_IM, LocalDate.now());

        //TAO ANATY TRY
        Sortie sortie = new Sortie();
        sortie.setPersonnel(personnel);
        //entry.getDate_enregistrement();

        if(currentplanning != null && (currentplanning.getFin_heure().isAfter(currentDateTime))){
            sortie.setIsEarly(true);
        }else if (!horaire.getFlexible() || horaire.getFin_horaire().isAfter(currentTime)) {
            sortie.setIsEarly(true);
        }else {
            sortie.setIsEarly(false);
        }

        //Associer avec un utilisateur
        sortie.setUtilisateur(user_scanneur);

        //VERIFIER SI sortie pendant la pause
        if((currentTime.isAfter(debut_pause) || currentTime.equals(debut_pause))
            && (currentTime.isBefore(fin_pause) || currentTime.equals(fin_pause))) {
            sortie.setPendant_pause(true);
            //throw new RuntimeException("verifie ny condition");
        }else {
            sortie.setPendant_pause(false);
            throw new RuntimeException("Tsa verifie ny condition");
        }

        Sortie savedSortie;

        if(lastscan instanceof Entry){
            sortie.setAssociated_entry((Entry) lastscan);
            savedSortie = sortieRepository.save(sortie);
        }else {
            Entry associated_entry = entryRepository.findLastEntryOfAPersonnel(personnel_IM);
            if(associated_entry != null && associated_entry.getAnswer_sortie() == null){
                sortie.setAssociated_entry(associated_entry);
                savedSortie = sortieRepository.save(sortie);
                throw  new LastScanIncompatibleException(lastscan, savedSortie);
            }else{
                savedSortie = sortieRepository.save(sortie);
                throw new NoCompatibleEntryRegisterdException("Aucune Entrée Compatible n'a été enregistré mais la sortie a ete enregistré |" + savedSortie.getId_scan().toString());
            }
        }
        return sortieDtoMapper.apply(savedSortie);
    }

    @Override
    public List<PresentPersonnelDto> getPresentPersonnel() {
        List<Entry> entries = entryRepository.findLastEntriesWithoutCorrespondingSortie();
        if(entries.isEmpty()){
            throw new ElementNotFoundException("Aucune entree trouvee");
        }
        return entries.stream().map(presentPersonnelDtoMapper)
                .collect(Collectors.toList());
    }
}
