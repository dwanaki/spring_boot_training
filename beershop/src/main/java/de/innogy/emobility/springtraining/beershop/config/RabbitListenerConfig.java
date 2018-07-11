package de.innogy.emobility.springtraining.beershop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

//TODO lesson 3.2: make the class a config bean and implement RabbitListenerConfigurer
public class RabbitListenerConfig {

    private ObjectMapper objectMapper;

    // TODO lesson 3.2 load DirectExchange-Name, DirectQueue-Namen und RoutingKey from properties

    // TODO lesson 3.3 load FanoutExchange-Name AND FanoutQueue-Namen from properties

    @Autowired
    public RabbitListenerConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // TODO lesson 3.2 setup DirectExchange, DirectQueue and Binding between Queue and Exchange

    // TODO lesson 3.3 setup FanoutExchange, FanoutQueue and Binding between Queue and Exchange


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
