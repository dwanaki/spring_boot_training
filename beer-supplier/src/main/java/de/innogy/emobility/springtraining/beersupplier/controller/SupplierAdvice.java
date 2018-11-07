package de.innogy.emobility.springtraining.beersupplier.controller;

import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SupplierAdvice {

    @ExceptionHandler(NotInStockException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bier existiert nicht")
    public void handleOutOfBeerException(NotInStockException e) {
    }
}
