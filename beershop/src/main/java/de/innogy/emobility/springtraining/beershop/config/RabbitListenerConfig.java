package de.innogy.emobility.springtraining.beershop.config;

import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

//TODO lesson 3.2: make the class a config bean and implement RabbitListenerConfigurer
public class RabbitListenerConfig {

    // TODO lesson 3.3 load FanoutExchange-Name AND FanoutQueue-Name from properties

    // TODO lesson 3.3 setup FanoutExchange, FanoutQueue and Binding between Queue and Exchange

    /**
     * Setup DefaultMessageHandlerMethodFactory to use MappingJackson2MessageConverter as MessageConverter.
     *
     * @return {@link DefaultMessageHandlerMethodFactory}
     */
    //TODO: Lesson 3.2 setup DefaultMessageHandlerMethodFactory to use MappingJackson2MessageConverter as MessageConverter
//    @Bean
//    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
//
//    }

    //TODO: Lesson 3.2 Override configureRabbitListeners Method and setMessageHandlerMethodFactory to use the DefaultMessageHandlerMethodFactory

}
