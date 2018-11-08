package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BeerService {

    private Map<String, Beer> availableBeer;

    @PostConstruct
    public void init() {
        availableBeer = new HashMap<>();
        availableBeer.put("Snake Venom",
                          Beer.builder().alcoholVol(67.5).name("Snake Venom").build());
        availableBeer.put("Sink the Bismarck!",
                          Beer.builder().alcoholVol(41.0).name("Sink the Bismarck!").build());
        availableBeer
                .put("Innogy Pils", Beer.builder().alcoholVol(5.0).name("Innogy Pils").build());
        availableBeer.put("Innogy Bock",
                          Beer.builder().alcoholVol(8.5).name("Innogy Bock").build());
        availableBeer.put("Faxe", Beer.builder().alcoholVol(5.0).name("Faxe").build());
        availableBeer.put("Elephant Beer",
                          Beer.builder().alcoholVol(11.0).name("Elephant Beer").build());
        availableBeer.put("Innogy Radler",
                          Beer.builder().alcoholVol(2.5).name("Innogy Radler").build());
        availableBeer.put("Innogy Alkoholfrei",
                          Beer.builder().alcoholVol(0.0).name("Innogy Alkoholfrei").build());
    }

    public Beer provideBeerByName(String name) throws NotInStockException {
        Beer providedBeer = availableBeer.get(name);
        if(providedBeer == null){
            log.info(name + "  is not listed.");
            throw new NotInStockException(name + " is not listed.");
        }
        return providedBeer;
    }

    public List<Beer> provideStock() {
        return new ArrayList<>(availableBeer.values());
    }

    public void addToStock(Beer beer) {
        availableBeer.put(beer.getName(), beer);
        log.info(beer.getName() + " was added to stock.");
    }

    public void removeBeer(String beerName) throws NotInStockException {
        Beer removedBeer = availableBeer.remove(beerName);
        if(removedBeer == null){
            log.info(beerName + " cannot be removed as it is not listed.");
            throw new NotInStockException(beerName + " cannot be removed as it is not listed.");
        }
        log.info(beerName + " was removed from stock.");
    }

    public void updateBeer(Beer beer) throws NotInStockException {
        availableBeer.put(provideBeerByName(beer.getName()).getName(), beer);
        log.info(beer.getName() + " was updated.");

    }

}
