package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDto;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitService {

    private RabbitTemplate rabbitTemplate;

    private FanoutExchange fanoutRemovedBeer;

    private Queue orderQueue;

    @Autowired
    public RabbitService(RabbitTemplate rabbitTemplate, Queue orderQueue, FanoutExchange fanoutRemovedBeer) {
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutRemovedBeer = fanoutRemovedBeer;
        this.orderQueue = orderQueue;
    }

    public void sendDelivery(DeliveryDto deliveryDTO) {
        rabbitTemplate.convertAndSend(orderQueue.getName(), deliveryDTO);
        log.info(deliveryDTO + " send to " + orderQueue.getName());
    }

    void sendRemovedBeerToFanout(Beer removedBeer) {
        rabbitTemplate.convertAndSend(fanoutRemovedBeer.getName(), "", removedBeer);
        log.info(removedBeer + " was removed from stock.");
    }
}
