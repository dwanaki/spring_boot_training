package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import de.innogy.emobility.springtraining.beersupplier.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BeerService {

    private BeerRepository beerRepository;

    @Autowired
    public BeerService(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @PostConstruct
    public void init() {
        List<Beer> beers = new ArrayList<>();
        beers.add(Beer.builder().alcoholVol(67.5).name("Snake Venom").bottleSizeInMl(500).build());
        beers.add(Beer.builder().alcoholVol(41.0).name("Sink the Bismarck!").bottleSizeInMl(375).build());
        beers.add(Beer.builder().alcoholVol(5.0).name("Innogy Pils").bottleSizeInMl(500).build());
        beers.add(Beer.builder().alcoholVol(8.5).name("Innogy Bock").bottleSizeInMl(5000).build());
        beers.add(Beer.builder().alcoholVol(5.0).name("Faxe").bottleSizeInMl(1000).build());
        beers.add(Beer.builder().alcoholVol(11.0).name("Elephant Beer").bottleSizeInMl(500).build());
        beers.add(Beer.builder().alcoholVol(2.5).name("Innogy Radler").bottleSizeInMl(500).build());
        beers.add(Beer.builder().alcoholVol(0.0).name("Innogy Alkoholfrei").bottleSizeInMl(500).build());
        beerRepository.saveAll(beers);
    }

    public Beer provideBeerByName(String name) throws NotInStockException {
        Beer providedBeer = beerRepository.findById(name).orElse(null);
        if(providedBeer == null){
            log.info(name + "  is not listed.");
            throw new NotInStockException(name + " is not listed.");
        }
        return providedBeer;
    }

    public List<Beer> provideStock() {
        return beerRepository.findAll();
    }

    public void addToStock(Beer beer) {
        beerRepository.save(beer);
        log.info(beer.getName() + " was added to stock.");
    }

    public void removeBeer(String beerName) throws NotInStockException {
        Beer removedBeer = beerRepository.findById(beerName).orElse(null);
        if(removedBeer == null){
            log.info(beerName + " cannot be removed as it is not listed.");
            throw new NotInStockException(beerName + " cannot be removed as it is not listed.");
        } else {
            beerRepository.deleteById(beerName);
        }
        log.info(beerName + " was removed from stock.");
    }

    public void updateBeer(Beer beer) throws NotInStockException {
        provideBeerByName(beer.getName());
        beerRepository.save(beer);
        log.info(beer.getName() + " was updated.");
    }

}
