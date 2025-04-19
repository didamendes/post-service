package br.com.diogomendes.post.service.infraestructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String FANOUT_EXCHANGE_NAME = "post-service.post-service.v1.e";
    public static final String QUEUE_POST_SERVICE = "post-service.post-processing-result.v1.q";

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(FANOUT_EXCHANGE_NAME).build();
    }

    @Bean
    public Queue queuePostService() {
        return QueueBuilder.durable(QUEUE_POST_SERVICE).build();
    }

    @Bean
    public Binding bindingPostService() {
        return BindingBuilder.bind(queuePostService()).to(exchange()).with("processorPost");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
