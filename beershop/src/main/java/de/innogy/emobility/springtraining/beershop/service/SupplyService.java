package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDto;
import de.innogy.emobility.springtraining.beershop.controller.OrderDto;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.exception.SorryDudeAlcoholicOnlyException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SupplyService {

    private RestTemplate restTemplate;

    @Value("${beersupplier.order.url}")
    private String beerProducerOrderUrl;

    @Value("${clientName}")
    private String clientName;

    @Setter
    private Map<String, BeerItem> beerItemList;

    @Autowired
    public SupplyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void fillSupplyWith(BeerItem beerItem) {
        try {
            DeliveryDto delivery = restTemplate.postForObject(beerProducerOrderUrl, new OrderDto(clientName, 1000, beerItem.getName()), DeliveryDto.class);
            if (delivery != null && delivery.getBeer() != null && delivery.getQuantity() > 0) {
                log.info("Received delivery of: {} quantity: {} ", delivery.getBeer().getName(), delivery.getQuantity());
                beerItem.setStock(beerItem.getStock() + delivery.getQuantity());
                // TODO: Save received beer to the database
                beerItemList.put(beerItem.getName(), beerItem);
            } else {
                log.warn("No delivery received, beer: {} still out of stock", beerItem.getName());
            }
        } catch (HttpClientErrorException e) {
            log.error("Could not order beer : {}, reponse code {}, error message: {}", beerItem.getName(), e.getRawStatusCode(), e.getMessage());
        }

    }

    public DeliveryDto orderBeer(OrderDto orderDTO) throws OutOfBeerException {
        BeerItem beerItem = beerItemList.get(orderDTO.getBeerName());
        if (beerItem != null && beerItem.getStock() >= orderDTO.getQuantity()) {
            beerItem.setStock(beerItem.getStock() - orderDTO.getQuantity());
            return new DeliveryDto(orderDTO.getQuantity(), beerItem);
        } else {
            BeerItem beerToOrder = beerItem != null ? beerItem : BeerItem.builder().name(orderDTO.getBeerName()).build();
            throw new OutOfBeerException(
                "Not enough quantity of Beer " + orderDTO.getBeerName() + " only " + (beerItem != null ? beerItem.getStock() : 0) + " left", beerToOrder);
        }
    }

    public List<BeerItem> provideMenu() {
        return new ArrayList<>(beerItemList.values());
    }

    public List<BeerItem> provideNonAlcholicBeer() throws SorryDudeAlcoholicOnlyException {
        //TODO: implement this method!
        return null;
    }

    public List<BeerItem> provideBeerDueForResupply() {
        //TODO: implement this method!
        return null;
    }
}
