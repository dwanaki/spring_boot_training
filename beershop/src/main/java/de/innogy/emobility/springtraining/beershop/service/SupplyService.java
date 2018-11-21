package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beershop.controller.OrderDTO;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@EnableConfigurationProperties(InitialStock.class)
public class SupplyService {
    
    private RestTemplate restTemplate;
    
    @Value("${beer-producer.order.url}")
    private String beerProducerOrderUrl;
    @Value("${beer-producer.supply.all.url}")
    private String beerProducerBeersUrl;
    @Value("${clientName}")
    private String clientName;
    
    private InitialStock initialStock;
    private Map<String, BeerItem> beerItemList;
    
    @Autowired
    public SupplyService(RestTemplate restTemplate, InitialStock initialStock) {
        this.restTemplate = restTemplate;
        this.initialStock = initialStock;
    }
    
    @PostConstruct
    public void init() {
        beerItemList = new HashMap<>();
        initialStock.beers.forEach(beerItem -> beerItemList.put(beerItem.getName(), beerItem));
    }
    
    public void refillAllStocks() {
        BeerItem[] beerItems = restTemplate.getForObject(beerProducerBeersUrl, BeerItem[].class);
        for (BeerItem beerItem : beerItems) {
            beerItem.setStock(100);
            beerItemList.put(beerItem.getName(), beerItem);
        }
    }
    
    public void fillSupplyWith(BeerItem beerItem) {
        DeliveryDTO order = restTemplate
                .postForObject(beerProducerOrderUrl, new OrderDTO(clientName, 1000, beerItem.getName()),
                        DeliveryDTO.class);
        beerItem.setStock(beerItem.getStock() + order.getQuantity());
        beerItemList.put(beerItem.getName(), beerItem);
    }
    
    public DeliveryDTO orderBeer(OrderDTO orderDTO) throws OutOfBeerException {
        if (!beerItemList.containsKey(orderDTO.getBeerName())) {
            throw new OutOfBeerException(
                    "No such Beer: " + orderDTO.getBeerName(), new BeerItem(orderDTO.getBeerName(), 0.0, orderDTO.getQuantity()));
        }
        BeerItem beerItem = beerItemList.get(orderDTO.getBeerName());
        if (beerItem.getStock() >= orderDTO.getQuantity()) {
            beerItem.setStock(beerItem.getStock() - orderDTO.getQuantity());
            return new DeliveryDTO(orderDTO.getQuantity(), beerItem);
        } else {
            throw new OutOfBeerException(
                    "Not enough quantity of Beer " + beerItem.getName() + " only " + beerItem.getStock() + " left", beerItem);
        }
    }
    
    public List<BeerItem> provideMenu() {
        return new ArrayList<>(beerItemList.values());
    }
}
