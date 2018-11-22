package de.innogy.emobility.springtraining.beersupplier.controller;

import de.innogy.emobility.springtraining.beersupplier.config.TestRabbitConfig;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrdersControllerTest {

    @Autowired
    private TestRabbitTemplate rabbitTemplate;

    @Autowired
    private TestRabbitConfig rabbitTestConfig;


}
