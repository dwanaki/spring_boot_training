package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutRemovedBeer;


    @Value("${queue.order}")
    private String deliveryQueue;

    public void sendDelivery(DeliveryDTO deliveryDTO){
        rabbitTemplate.convertAndSend(deliveryQueue, deliveryDTO);
        log.info(deliveryDTO + " send to " + deliveryQueue);
    }

    public void sendRemovedBeerToFanout(String removedBeer) {
        rabbitTemplate.convertAndSend(fanoutRemovedBeer.getName(), "", removedBeer);
        log.info(removedBeer + " was removed from stock.");
    }

}
