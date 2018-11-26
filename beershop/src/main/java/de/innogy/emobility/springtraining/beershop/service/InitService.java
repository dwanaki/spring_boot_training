package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Component that will request the inital stock of beer from the supplier.
 */
@Slf4j
@Component
public class InitService implements ApplicationRunner {

    private RestTemplate restTemplate;

    // TODO: Replace the service by your created Repository.
    private SupplyService supplyService;

    @Value("${beersupplier.supply.all.url}")
    private String beerProducerBeersUrl;

    @Autowired
    public InitService(RestTemplate restTemplate, SupplyService supplyService) {
        this.restTemplate = restTemplate;
        this.supplyService = supplyService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application started, getting beer from provider.");
        BeerItem[] beerItems = restTemplate.getForObject(beerProducerBeersUrl, BeerItem[].class);
        Map<String, BeerItem> beerItemList = new HashMap<>();
        // TODO: Save the received beers to the database using the repositroy instead of writing them to a map.
        if (beerItems != null) {
            for (BeerItem beerItem : beerItems) {
                beerItem.setStock(100);
                beerItemList.put(beerItem.getName(), beerItem);
            }
            supplyService.setBeerItemList(beerItemList);
            log.info("Received {} sorts of beer", beerItems.length);
        } else {
            log.error("Did not receive beer.");
        }
    }
}
