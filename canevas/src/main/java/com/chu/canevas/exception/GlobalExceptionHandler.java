package com.chu.canevas.exception;

import com.chu.canevas.dto.Scan.IncompatibleScanResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ElementNotFoundException.class) //404
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleElementNotFoundException(ElementNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementDuplicationException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleElementDuplicationException(ElementDuplicationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach( error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /// ********** SCAN *************** ///
    @ExceptionHandler(LastScanIncompatibleException.class)
    public ResponseEntity<IncompatibleScanResponse> handleLastScanIncompatibleException(LastScanIncompatibleException e){
//        IncompatibleScanResponse incompatibleScanResponse = new IncompatibleScanResponse(
//                e.getMessage(),
//                e.getLastScan().getId_scan(), e.getLastScan().getDate_enregistrement(),
//                e.getRegisteredScan().getId_scan(), e.getRegisteredScan().getDate_enregistrement()
//        );
        return new ResponseEntity<>(
                new IncompatibleScanResponse(e.getMessage(), e.getLastScan().getId_scan(), e.getLastScan().getDate_enregistrement()),
                HttpStatus.EXPECTATION_FAILED
        );
    }
//    @ExceptionHandler(LastScanIncompatibleException.class)
//    public ResponseEntity<String> handleLastScanIncompatibleException(LastScanIncompatibleException e){
//        //IncompatibleScanResponse incompatibleScanResponse = new IncompatibleScanResponse(e.getMessage(), e.getRegisteredScan(), e.getLastScan());
//        return new ResponseEntity<>(
//                e.getMessage(),
//                HttpStatus.EXPECTATION_FAILED
//        );
//    }

    @ExceptionHandler(NoCompatibleEntryRegisterdException.class)
    public ResponseEntity<String> handleNoEntryRegisterdException(NoCompatibleEntryRegisterdException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    //######## Absence ##########
    @ExceptionHandler(SamePersonAsInterimException.class)
    public ResponseEntity<String> handleSamePersonAsInterimException(SamePersonAsInterimException e){
        return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
