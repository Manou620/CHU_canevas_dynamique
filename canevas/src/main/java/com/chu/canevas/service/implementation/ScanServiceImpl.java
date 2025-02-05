package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.dto.RealTimeData.EntryExit;
import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.dto.Scan.ScanDTO;
import com.chu.canevas.dto.Scan.SortieResponseDTO;
import com.chu.canevas.dto.dtoMapper.EntryDtoMapper;
import com.chu.canevas.dto.dtoMapper.PresentPersonnelDtoMapper;
import com.chu.canevas.dto.dtoMapper.SortieDtoMapper;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.exception.LastScanIncompatibleException;
import com.chu.canevas.model.*;
import com.chu.canevas.repository.*;
import com.chu.canevas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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
    private GardeRepository gardeRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EntryDtoMapper entryDtoMapper;

    @Autowired
    private SortieDtoMapper sortieDtoMapper;

    @Autowired
    private PresentPersonnelDtoMapper presentPersonnelDtoMapper;

//    @Autowired
//    private ScanRepository scanRepository;

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
            LocalTime thresholdTime = LocalTime.of(8, 0);

            //Verifiena hoe misy ve ny employee
            Personnel personnel = personnelRepository.findById(personnel_IM).orElseThrow(
                    () -> new ElementNotFoundException("Personnel", personnel_IM)
            );

            //Last entry
            Scan lastscan = getLastScanRegistered(personnel);
            if(lastscan instanceof Entry){
                throw new RuntimeException("Le dernier enregistrement pour cet employé etait une entrée");
            }

            //afaka atao anaty condition
            Utilisateur user_scanneur = utilisateurRepository.findById(user_id).orElseThrow(
                    () -> new ElementNotFoundException("Utilisateur", user_id.toString())
            );

            //#########Conge & autorisé à s'absenter sa tsia

            // Mila horairen'ny employe
            Horaire horaire = personnel.getHoraire();

            //Check reh misy garde
            Garde todayGarde = gardeRepository.findFirstByPersonnelAndDate(personnel_IM, LocalDate.now());


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
                entry.setHeureAttendu("ENTRY PLANNING " + currentplanning.getDebut_heure().toString());
                if(currentplanning.getDebut_heure().isBefore(currentDateTime) || currentplanning.getDebut_heure().equals(currentDateTime)){
                    entry.setIs_late(true);
                }
            }else if (todayGarde != null) {
                entry.setHeureAttendu("ENTRY GARDE " + thresholdTime.toString());
                if(currentTime.isAfter(thresholdTime)){
                    entry.setIs_late(true);
                }
            }else{
//                if(!horaire.getFlexible() && (horaire.getDebut_horaire().isBefore(currentTime) || horaire.getDebut_horaire().equals(currentDateTime))){
//                    entry.setIs_late(true);
//                }
                entry.setHeureAttendu("ENTRY HORAIRE " + horaire.getDebut_horaire().toString());
                if(horaire.getDebut_horaire().isBefore(currentTime) || horaire.getDebut_horaire().equals(currentDateTime)){
                    entry.setIs_late(true);
                }
            }

            //First Entry ou pas
            if (isFirstEntry){
                entry.setFirst_entry(true);
            }

            //Associer avec un utilisateur
            entry.setUtilisateur(user_scanneur);

            entry.setType_horaire_attendu(personnel.getHoraire().getId());

            entry.setAnswer_sortie(null);

//            System.out.println("Sortie no farany tao");
//            System.out.println((Sortie) lastscan);
//            System.out.println("ENtree a enregistrer : ");
//            System.out.println(entry);
            //FIN TAO ANATY TRY

            Entry savedEntry = entryRepository.save(entry);
//            if(lastscan instanceof Entry){
//                System.out.println("entree");
//                System.out.println((Entry) lastscan);
//                throw new LastScanIncompatibleException(lastscan, savedEntry);
////                throw new LastScanIncompatibleException(lastscan);
//            }

            return entryDtoMapper.apply(savedEntry);

    }

    //############# enregistrer une sortie
    //mila fantarina ko ve ny horaire sa de efa last Entry fotsiny no valina
    @Override
    public SortieResponseDTO registerSortie(String personnel_IM, Long user_id) {
        Instant scanMoment = Instant.now();
        ZonedDateTime zonedDateTime = scanMoment.atZone(ZoneId.systemDefault());
        LocalDate currentDate = zonedDateTime.toLocalDate();
        LocalTime currentTime = zonedDateTime.toLocalTime();
        LocalTime thresholdTime = LocalTime.of(8, 0);
        LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();

        LocalTime debut_pause = LocalTime.parse("12:00:00");
        LocalTime fin_pause = LocalTime.parse("13:30:00");

        //Verifiena hoe misy ve ny employee
        Personnel personnel = new Personnel();
        if(personnel_IM != null){
            personnel =  personnelRepository.findById(personnel_IM).orElseThrow(
                () -> new ElementNotFoundException("Personnel", personnel_IM)
            );
        }else {
            throw new RuntimeException("Des données vides on été envoyées");
        }

        //Jerena ny last Scan
        Scan lastscan = getLastScanRegistered(personnel);

        //afaka atao anaty condition
        Utilisateur user_scanneur = utilisateurRepository.findById(user_id).orElseThrow(
                () -> new  ElementNotFoundException("Utilisateur", user_id.toString())
        );

        //#########Conge & autorisé à s'absenter sa tsia

        // Mila horairen'ny employe ve?
        Horaire horaire = personnel.getHoraire();

        /// ###### Hijerena ho is_Late sa tsia

        //remontena ny planning sy horaire
        Planning currentplanning = planningRepository.getCurrentPlanningOfAPersonnel(personnel_IM, LocalDate.now(), LocalDateTime.now());
        //Jerena reh first entree
        Boolean isFirstEntry = entryRepository.findEntryOfADateForAPersonnel(personnel_IM, LocalDate.now());

        //Garde gardeYesterday = gardeRepository.findFirstByPersonnelAndDate(personnel_IM, LocalDate.now().minusDays(1));

        //TAO ANATY TRY
        Sortie sortie = new Sortie();
        sortie.setPersonnel(personnel);
        //entry.getDate_enregistrement();

        if(currentplanning != null && (currentplanning.getFin_heure().isAfter(currentDateTime))) {
            sortie.setIsEarly(true);
            sortie.setHeureAttendu("EXIT PLANNING " + currentplanning.getFin_heure().toString());
        }else if (!horaire.getFlexible() || horaire.getFin_horaire().isAfter(currentTime)) {
            sortie.setIsEarly(true);
            sortie.setHeureAttendu("EXIT HORAIRE " + horaire.getFin_horaire().toString());
        }else {
            if(currentplanning != null){
                sortie.setHeureAttendu("EXIT PLANNING " + currentplanning.getFin_heure().toString());
            }else{
                sortie.setHeureAttendu("EXIT HORAIRE " + horaire.getFin_horaire().toString());
            }
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
            //throw new RuntimeException("Tsa verifie ny condition");
        }

        Sortie savedSortie;
        SortieResponseDTO sortieResponseDTO = new SortieResponseDTO();

        if(lastscan instanceof Entry){
            sortie.setAssociated_entry((Entry) lastscan);
            savedSortie = sortieRepository.save(sortie);
            sortieResponseDTO.setMessage("Sortie enregisté avec succes");
            sortieResponseDTO.setStatus(200);
            sortieResponseDTO.setSortie_type("SUCCESS");
        }else {
            throw  new RuntimeException("Cet employé n'est pas encore entré");
//            Entry associated_entry = entryRepository.findLastEntryOfAPersonnel(personnel_IM);
//            sortieResponseDTO.setStatus(201);
//            if(associated_entry != null && associated_entry.getAnswer_sortie() == null){
//                sortie.setAssociated_entry(associated_entry);
//                savedSortie = sortieRepository.save(sortie);
//                sortieResponseDTO.setMessage("Pas d'entrée trouvé justifiant la sortie, le dernier scan a été une sortie");
//                sortieResponseDTO.setSortie_type("LAST-SCAN-IS-SORTIE");
//                //throw  new LastScanIncompatibleException(lastscan, savedSortie);
//            }else{
//                savedSortie = sortieRepository.save(sortie);
//                sortieResponseDTO.setMessage("Pas d'entrée encore enregistré");
//                sortieResponseDTO.setSortie_type("NO-ENTRY");
//                //throw new NoCompatibleEntryRegisterdException("Aucune Entrée Compatible n'a été enregistré mais la sortie a ete enregistré |" + savedSortie.getId_scan().toString());
//            }
        }
        sortieResponseDTO.setSortieDTO(sortieDtoMapper.apply(savedSortie));
        return sortieResponseDTO;
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

    @Override
    public long countLateFirstEntriesOfToday() {
        Instant startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);
        return entryRepository.countLateFirstEntriesToday(startOfDay, endOfDay);
    }

    @Override
    public long countEntriesOfToday() {
        Instant startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);
        return entryRepository.countEntriesToday(startOfDay, endOfDay);
    }

    @Override
    public long countSortiesOfToday() {
        Instant startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);
        return sortieRepository.countExitsToday(startOfDay, endOfDay);
    }

    @Override
    public long countEarlyExistsOfToday() {
        Instant startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);
        return sortieRepository.countEarlyExitsToday(startOfDay, endOfDay);
    }

    @Override
    public EntryExit getCountedEntryExitOfToday() {
        Instant startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);
        return new EntryExit(
                entryRepository.countEntriesToday(startOfDay, endOfDay),
                sortieRepository.countExitsToday(startOfDay, endOfDay),
                entryRepository.countLateFirstEntriesToday(startOfDay, endOfDay),
                sortieRepository.countEarlyExitsToday(startOfDay, endOfDay)
        );
    }

    @Override
    public List<ScanDTO> getLast5ScansOfPersonnel(String personnelId) {
        Pageable top5 = PageRequest.of(0, 5);
        // Get last 5 entries and exits
        List<Entry> lastEntries = entryRepository.findTop5ByPersonnelOrderByDateEnregistrementDesc(personnelId, top5);
        List<Sortie> lastExits = sortieRepository.findTop5ByPersonnelOrderByDateEnregistrementDesc(personnelId, top5);

        // Combine and sort by date
        List<Scan> allScans = new ArrayList<>();
        allScans.addAll(lastEntries);
        allScans.addAll(lastExits);

        allScans.sort(Comparator.comparing(Scan::getDateEnregistrement).reversed());

        // Convert to DTOs
        return allScans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ScanDTO> getLast30Scans() {
        Pageable top30 = PageRequest.of(0, 15);
        // Get last 5 entries and exits
        List<Entry> lastEntries = entryRepository.findTop15OrderByDateEnregistrementDesc(top30);
        List<Sortie> lastExits = sortieRepository.findTop15OrderByDateEnregistrementDesc(top30);

        // Combine and sort by date
        List<Scan> allScans = new ArrayList<>();
        allScans.addAll(lastEntries);
        allScans.addAll(lastExits);

        allScans.sort(Comparator.comparing(Scan::getDateEnregistrement).reversed());

        // Convert to DTOs
        return allScans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ScanDTO convertToDTO(Scan scan) {
        boolean isLateEntry = scan instanceof Entry && Boolean.TRUE.equals(((Entry) scan).getIs_late());
        boolean isFirstEntry = scan instanceof Entry && Boolean.TRUE.equals(((Entry) scan).getFirst_entry());
        boolean isEarlyExit = scan instanceof Sortie && Boolean.TRUE.equals(((Sortie) scan).getIsEarly());

        String scanType = scan instanceof Entry ? "ENTRY" : "EXIT";
        return new ScanDTO(
                scan.getIdScan(),
                scanType,
                scan.getObservation(),
                scan.getPersonnel().getNom(),
                scan.getPersonnel().getImmatriculation(),
                isLateEntry,
                isEarlyExit,
                isFirstEntry,
                scan.getPersonnel().getService().getNomService(),
                scan.getUtilisateur().getNom_utilisateur(),
                scan.getDateEnregistrement()
        );
    }

//    @Override
//    public Page<ScanDTO> getFilteredScans(String personnelName, Short service, String immatriculation, Instant startDate, Instant endDate, Pageable pageable) {
//        Specification<Scan> spec = Specification
//                .where(ScanSpecification.flterByPersonnelName(personnelName))
//                .and(ScanSpecification.filterByService(service))
//                .and(ScanSpecification.filterByImmatriculation(immatriculation))
//                .and(ScanSpecification.filterByDateRange(startDate, endDate));
//
//        return scanRepository.findAll(spec, pageable)
//                .map(this::convertToDTO);
//    }
//
//    private ScanDTO convertToDTO(Scan scan) {
//        boolean isLateEntry = scan instanceof Entry && Boolean.TRUE.equals(((Entry) scan).getIs_late());
//        boolean isEarlyExit = scan instanceof Sortie && Boolean.TRUE.equals(((Sortie) scan).getIsEarly());
//        boolean isFirstEntry = scan instanceof Entry && Boolean.TRUE.equals(((Entry) scan).getFirst_entry());
//
//        // Retrieve personnel and service info
//        String personnelName = scan.getPersonnel() != null
//                ? scan.getPersonnel().getNom()                : null;
//        String service = scan.getPersonnel() != null
//                ? scan.getPersonnel().getService().getNomService()
//                : null;
//
//        assert scan.getPersonnel() != null;
//        return new ScanDTO(
//                scan.getId_scan(),
//                scan instanceof Entry ? "ENTRY" : "EXIT",
//                scan.getObservation(),
//                personnelName,
//                scan.getPersonnel().getImmatriculation(),
//                isLateEntry,
//                isEarlyExit,
//                isFirstEntry,
//                isEarlyExit, // Adjust for "last exit" logic
//                service
//        );
//    }

}
