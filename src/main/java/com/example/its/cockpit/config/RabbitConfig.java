package com.example.its.cockpit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String DATA_EXCHANGER = "data";
    public static final String ACTION_EXHANGER = "action";
    public static final String DATA_ROOMBA_QUEUE = "roombadatacockpit";
    public static final String DATA_ITS_QUEUE = "itsdatacockpit";
    public static final String DATA_ROOMBA_ROUTING_KEY = "data.roomba";
    public static final String DATA_ITS_ROUTING_KEY = "data.its";


    @Bean(ACTION_EXHANGER)
    public TopicExchange actionExchange() {
        return new TopicExchange(ACTION_EXHANGER, false, false);
    }

    @Bean(DATA_EXCHANGER)
    public TopicExchange dataExchange() {
        return new TopicExchange(DATA_EXCHANGER, false, false);
    }

    @Bean(DATA_ROOMBA_QUEUE)
    public Queue dataRoombaQueue() {
        return new Queue(DATA_ROOMBA_QUEUE, false, true, true);
    }

    @Bean(DATA_ITS_QUEUE)
    public Queue dataItsQueue() {
        return new Queue(DATA_ITS_QUEUE, false, true, true);
    }

    @Bean
    public Binding dataRoombaBinding(@Qualifier(DATA_ROOMBA_QUEUE) Queue queue, @Qualifier(DATA_EXCHANGER) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DATA_ROOMBA_ROUTING_KEY);
    }

    @Bean
    public Binding dataItsBinding(@Qualifier(DATA_ITS_QUEUE) Queue queue, @Qualifier(DATA_EXCHANGER) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DATA_ITS_ROUTING_KEY);
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
