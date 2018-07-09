package de.innogy.emobility.springtraining.beershop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

//TODO Aufgabe 3.2: Als Config Bean annotieren und RabbitListenerConfigurer implementieren
public class RabbitListenerConfig {

    private ObjectMapper objectMapper;

    // TODO Aufgabe 3.2 DirectExchangeNamen, DirectQueue-Namen und RoutingKey aus properties laden.

    // TODO Aufgabe 3.3 FanoutExchangeNamen und FanoutQueue-Namen aus properties laden.

    @Autowired
    public RabbitListenerConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // TODO Aufgabe 3.2 DirectExchange, DirectQueue und Binding für Queue und Exchange als Beans anlegen

    // TODO Aufgabe 3.3 FanoutExchange, FanoutQueue und Binding für Queue und Exchange als Beans anlegen


//    /**
//     * Setup MessageConverter for JSON-Payload deserialization.
//     *
//     * @return {@link MappingJackson2MessageConverter}
//     */
//    @Bean
//    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
//        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
//        messageConverter.setObjectMapper(objectMapper);
//        return messageConverter;
//    }
//
//    /**
//     * Setup DefaultMessageHandlerMethodFactory to use Jackson as MessageConverter.
//     *
//     * @return {@link DefaultMessageHandlerMethodFactory}
//     */
//    @Bean
//    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
//        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//        factory.setMessageConverter(consumerJackson2MessageConverter());
//        return factory;
//    }

}
