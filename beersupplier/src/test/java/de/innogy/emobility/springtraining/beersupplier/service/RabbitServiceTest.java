package de.innogy.emobility.springtraining.beersupplier.service;

import de.innogy.emobility.springtraining.beersupplier.config.TestRabbitConfig;
import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDto;
import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Import(TestRabbitConfig.class)
@ActiveProfiles("test")
public class RabbitServiceTest {

    @Autowired
    private TestRabbitTemplate testRabbitTemplate;

    @Autowired
    private Queue orderTestQueue;

    @Autowired
    private FanoutExchange testFanoutExchange;

    @Autowired
    private TestRabbitConfig testRabbitConfig;

    private RabbitService rabbitService;

    @Before
    public void setUp() {
        rabbitService = new RabbitService(testRabbitTemplate, orderTestQueue, testFanoutExchange);
    }

    @Test
    public void sendDelivery() {
        Beer beer = Beer.builder().alcoholVol(6.2).name("Test Beer").build();
        DeliveryDto deliveryDTO = new DeliveryDto(5, beer);

        rabbitService.sendDelivery(deliveryDTO);
        List<DeliveryDto> deliveries = testRabbitConfig.getReceivedDeliveries();
        assertThat(deliveries).hasSize(1);
        assertThat(deliveries.get(0)).isEqualTo(deliveryDTO);
    }

    @After
    public void cleanUp(){
        testRabbitConfig.clearReceivedMessages();
    }
}