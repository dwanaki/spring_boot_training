package de.innogy.emobility.springtraining.beershop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitListenerConfig implements RabbitListenerConfigurer {

    @Value("${fanout.removedBeer}")
    private String fanoutRemoveBeer;

    @Value("${fanout.queue.removedBeer}")
    private String fanoutQueueRemoveBeer;

    @Value("${queue.order}")
    private String orderQueue;

    /**
     * Setup FanoutExchange.
     *
     * @return {@link FanoutExchange}
     */
    @Bean
    public FanoutExchange fanoutRemoveBeer() {
        return new FanoutExchange(fanoutRemoveBeer);
    }

    /**
     * Setup Queue for FanoutExchange.
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue fanoutRemoveBeerQueue() {
        return new Queue(fanoutQueueRemoveBeer);
    }

    /**
     * Bind fanoutExchange-queue to fanoutExchange-exchange
     *
     * @return {@link Binding} of fanoutExchange-queue
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutRemoveBeerQueue()).to(fanoutRemoveBeer());
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
}
