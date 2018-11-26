package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

/**
 * Component that will request the inital stock of beer from the supplier.
 */
@Slf4j
@Component
public class InitService implements ApplicationRunner {

    private RestTemplate restTemplate;

    private BeerItemRepository beerItemRepository;

    @Value("${beersupplier.supply.all.url}")
    private String beerProducerBeersUrl;

    @Autowired
    public InitService(RestTemplate restTemplate, BeerItemRepository beerItemRepository) {
        this.restTemplate = restTemplate;
        this.beerItemRepository = beerItemRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application started, getting beer from provider.");
        BeerItem[] beerItems = restTemplate.getForObject(UriComponentsBuilder.fromUriString(beerProducerBeersUrl).build().toUri(), BeerItem[].class);
        if (beerItems != null) {
            for (BeerItem beerItem : beerItems) {
                beerItem.setStock(100);
            }
            log.info("Received {} sorts of beer", beerItems.length);
            beerItemRepository.saveAll(Arrays.asList(beerItems));
        } else {
            log.error("Did not receive beer.");
        }
    }
}
