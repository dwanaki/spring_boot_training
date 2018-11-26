package de.innogy.emobility.springtraining.beersupplier.config;

import com.rabbitmq.client.Channel;
import de.innogy.emobility.springtraining.beersupplier.controller.DeliveryDto;
import lombok.Getter;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

//lesson 3.4.2: make it a test config bean that implements (like in beershop rabbitconfig)
@EnableRabbit
@TestConfiguration
@Profile("test")
public class TestRabbitConfig implements RabbitListenerConfigurer {

    @Value("${queue.order}")
    private String orderTestQueue;

    @Getter
    private List<DeliveryDto> receivedDeliveries = new ArrayList<>();

    @Bean
    public Queue orderTestQueue() {
        return new Queue(orderTestQueue);
    }

    /**
     * FanoutExchange as Bean to be able to autowire it
     *
     * @param fanoutExchange name of the exchange from properties
     *
     * @return {@link FanoutExchange}
     */
    @Bean
    public FanoutExchange testFanoutExchange(@Value("${fanout.removedBeer}") String fanoutExchange) {
        return new FanoutExchange(fanoutExchange);
    }


    /**
     * Setup TestRabbitTemplate
     *
     * @return {@link TestRabbitTemplate}
     */
    @Bean
    public TestRabbitTemplate testRabbitTemplate() {
        TestRabbitTemplate testRabbitTemplate = new TestRabbitTemplate(connectionFactory());
        testRabbitTemplate.setMessageConverter(amqpJsonConverter());
        return testRabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter amqpJsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Setup the Connection factory that will mock/simulate connections Rabbit.
     *
     * @return Connection factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        Channel channel = mock(Channel.class);
        willReturn(connection).given(factory).createConnection();
        willReturn(channel).given(connection).createChannel(anyBoolean());
        given(channel.isOpen()).willReturn(true);
        return factory;
    }

    /**
     * Setup DefaultMessageHandlerMethodFactory to use Jackson as MessageConverter.
     *
     * @return {@link DefaultMessageHandlerMethodFactory}
     */
    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @RabbitListener(queues = "#{orderTestQueue.name}")
    public void receivedOrder(DeliveryDto deliveryDTO) {
        receivedDeliveries.add(deliveryDTO);
    }

    public void clearReceivedMessages() {
        receivedDeliveries.clear();
    }

}
