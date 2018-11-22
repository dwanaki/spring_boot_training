package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService {

    private RabbitTemplate rabbitTemplate;

    private Queue orderQueue;

//    private FanoutExchange fanoutRemovedBeer;

    @Autowired
    public RabbitService(RabbitTemplate rabbitTemplate, Queue orderQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderQueue = orderQueue;
    }

    public void sendDelivery(DeliveryDTO deliveryDTO) {
        rabbitTemplate.convertAndSend(orderQueue.getName(), deliveryDTO);
        log.info(deliveryDTO + " send to " + orderQueue.getName());
    }

    public void sendRemovedBeerToFanout(String removedBeer) {
//        rabbitTemplate.convertAndSend(fanoutRemovedBeer.getName(), "", removedBeer);
//        log.info(removedBeer + " was removed from stock.");
        //do nothing now
    }

}
