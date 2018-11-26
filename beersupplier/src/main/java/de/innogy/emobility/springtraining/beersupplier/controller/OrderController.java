package de.innogy.emobility.springtraining.beersupplier.controller;

import de.innogy.emobility.springtraining.beersupplier.exception.NotInStockException;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import de.innogy.emobility.springtraining.beersupplier.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/order")
public class OrderController {

    private BeerService beerService;

    @Autowired
    public OrderController(BeerService beerService) {
        this.beerService = beerService;
    }

    // TODO lesson 3.2: wire RabbitService and send DeliveryDto via rabbitmq
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryDto placeOrder(@Valid @RequestBody OrderDto order) throws NotInStockException {
        String beerName = order.getBeerName();
        Beer beer = beerService.provideBeerByName(beerName);
        return new DeliveryDto(order.getQuantity(), beer);
    }

}
