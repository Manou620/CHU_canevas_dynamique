package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.Scan.EntryDTO;
import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.model.Entry;
import com.chu.canevas.model.Scan;
import com.chu.canevas.model.Sortie;
import com.chu.canevas.repository.EntryRepository;
import com.chu.canevas.repository.SortieRepository;
import com.chu.canevas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScanServiceImpl  implements ScanService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private SortieRepository sortieRepository;

    /**
     * @param personnel_IM
     * @return
     */
    @Override
    public Scan getLastScanRegistered(String personnel_IM) {
        Scan lastScan = entryRepository.findLastScanMade(personnel_IM);
        if(entryRepository.findLastScanMade(personnel_IM) != null) {
            return lastScan;
        }else{
            return null;
            //throw new ElementNotFoundException("Pas d'enregistrement trouvé");
        }
    }

//    public EntryDTO registerEntry(String personnel_IM){
//        //Mila horairen'ny employe
//
//        //Jerena reh first entree
//        Scan lastscan = getLastScanRegistered(personnel_IM);
//        if(lastscan instanceof Entry){
//            System.out.println("entree");
//            System.out.println((Entry) lastscan);
//        } else if (lastscan instanceof Sortie || lastscan == null) {
//            System.out.println("Sortie no farany tao");
//            System.out.println((Sortie) lastscan);
//
//        } else {
//            System.out.println("Sortie");
//        }
//    }
}
