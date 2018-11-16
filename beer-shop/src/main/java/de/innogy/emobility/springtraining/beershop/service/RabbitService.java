package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RabbitService {

    private BeerItemRepository beerItemRepository;

    @Autowired
    public RabbitService(BeerItemRepository beerItemRepository) {
        this.beerItemRepository = beerItemRepository;
    }

    @RabbitListener(queues = "queue.order")
    private void receiveDelivery(DeliveryDTO deliveredBeer) {
        if (deliveredBeer != null) {
            log.info("Received order: Beer: {}", deliveredBeer.getBeer().getName());
            Optional<BeerItem> result = beerItemRepository.findById(deliveredBeer.getBeer().getName());
            if (result.isPresent()) {
                BeerItem currentBeer = result.get();
                currentBeer.setStock(currentBeer.getStock() + 10);
                beerItemRepository.save(currentBeer);
            }
        }
    }

    @RabbitListener(queues = "#{fanoutRemoveBeerQueue.name}")
    private void removeBeer(BeerItem beerItem) {
        beerItemRepository.delete(beerItem);
        log.info(beerItem.getName() + " was removed from database");
    }

}
