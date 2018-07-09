package de.innogy.emobility.springtraining.beersupplier.controller;

import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import de.innogy.emobility.springtraining.beersupplier.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyController {

    private BeerService beerService;

    @Autowired
    public SupplyController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping(value = "/add")
    public void addBeer(@Valid @RequestBody Beer beer) {
        beerService.addToStock(beer);
    }

    @PutMapping(value = "/update")
    public void updateBeer(@Valid @RequestBody Beer beer) throws NotInStockException {
        beerService.updateBeer(beer);
    }

    @PostMapping(value = "/remove")
    public void deleteBeer(@RequestBody String name) throws NotInStockException {
        beerService.removeBeer(name);
    }

    @GetMapping("/all")
    public List<Beer> getAll() {
        return beerService.provideStock();
    }

}
