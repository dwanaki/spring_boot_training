package de.innogy.emobility.springtraining.beersupplier.controller;

import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import de.innogy.emobility.springtraining.beersupplier.service.BeerService;
import de.innogy.emobility.springtraining.beersupplier.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/order")
public class OrderController {

    @Autowired
    private BeerService beerService;

    @Autowired
    private RabbitService rabbitService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void placeOrder(@Valid @RequestBody OrderDTO order) throws NotInStockException {
        String beerName = order.getBeerName();
        Beer beer = beerService.provideBeerByName(beerName);
        rabbitService.sendDelivery(new DeliveryDTO(order.getQuantity(), beer));
    }

}
