package de.innogy.emobility.springtraining.beershop.exception;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.Getter;

public class OutOfBeerException extends Exception {

    @Getter
    private BeerItem beerItem;

    public OutOfBeerException(String msg, BeerItem beerItem) {
        super(msg);
        this.beerItem = beerItem;
    }
}
