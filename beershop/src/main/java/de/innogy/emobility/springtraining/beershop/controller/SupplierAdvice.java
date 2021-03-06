package de.innogy.emobility.springtraining.beershop.controller;

import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.exception.SorryDudeAlcoholicOnlyException;
import de.innogy.emobility.springtraining.beershop.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SupplierAdvice {

    @Autowired
    private SupplyService supplyService;

    @ExceptionHandler(OutOfBeerException.class)
    @ResponseStatus(code = HttpStatus.GONE, reason = "Bier ist alle")
    public void handleOutOfBeerException(OutOfBeerException e) {
        //TODO: write your JDBC statement(s) here!
        supplyService.fillSupplyWith(e.getBeerItem());
    }

    @ExceptionHandler(SorryDudeAlcoholicOnlyException.class)
    @ResponseStatus(code=HttpStatus.I_AM_A_TEAPOT, reason="I am no teapot!")
    public void handleSorryAlcoholicOnlyDudeException(SorryDudeAlcoholicOnlyException e) {
        //only send error code to the client
    }

}
