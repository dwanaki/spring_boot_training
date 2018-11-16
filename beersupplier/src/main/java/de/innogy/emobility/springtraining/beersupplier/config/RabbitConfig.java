package de.innogy.emobility.springtraining.beersupplier.config;

//TODO lesson 3.1: make it a config bean
public class RabbitConfig {

    //TODO: lesson 3.1: load queue-name from application.properties and setup the queue

    //TODO: lesson 3.3: setup FanoutExchange

    /**
     * Create a Bean for RabbitTemplate to be able to autowire it.
     * Just needed to set the messageConverter or other properties
     *
     * @param connectionFactory ConnectionFactory
     *
     * @return RabbitTemplate
     */
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
    //TODO: lesson 3.2: use Jackson2JsonMessageConverter as message converter
//    }

}
