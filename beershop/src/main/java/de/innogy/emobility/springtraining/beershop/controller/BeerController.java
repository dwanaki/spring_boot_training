package de.innogy.emobility.springtraining.beershop.controller;

import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BeerController {

    private SupplyService supplyService;

    @Autowired
    public BeerController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping("/order")
    public DeliveryDTO orderBeer(@Valid @RequestBody OrderDTO orderDTO)
            throws OutOfBeerException {
        return supplyService.orderBeer(orderDTO);
    }

    @GetMapping("/menu")
    public List<BeerItem> getMenu() {
        return supplyService.provideMenu();
    }

}
