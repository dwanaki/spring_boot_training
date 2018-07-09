package de.innogy.emobility.springtraining.beershop.controller;

import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.exception.SorryAlcoholicOnlyDudeException;
import de.innogy.emobility.springtraining.beershop.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SupplierAdvice {

    private SupplyService supplyService;

    @Autowired
    public SupplierAdvice(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @ExceptionHandler(OutOfBeerException.class)
    @ResponseStatus(code = HttpStatus.GONE, reason = "Bier ist alle")
    public void handleOutOfBeerException(OutOfBeerException e) {

        supplyService.fillSupplyWith(e.getBeerItem());
    }

    @ExceptionHandler(SorryAlcoholicOnlyDudeException.class)
    @ResponseStatus(code=HttpStatus.I_AM_A_TEAPOT, reason="I am no teapot!")
    public void handleSorryAlcoholicOnlyDudeException(SorryAlcoholicOnlyDudeException e) {

    }

}
