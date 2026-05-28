package com.dontworry.crawler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String CRAWLER_EXCHANGE = "crawler.exchange";

    public static final String KEYWORD_QUEUE = "crawler.keyword.queue";
    public static final String KEYWORD_ROUTING_KEY = "crawler.keyword";

    @Bean
    public Declarables crawlerDeclarables() {
        TopicExchange crawlerExchange = new TopicExchange(CRAWLER_EXCHANGE);

        Queue keywordQueue = new Queue(KEYWORD_QUEUE, true);

        Binding keywordBinding = BindingBuilder
                .bind(keywordQueue)
                .to(crawlerExchange)
                .with(KEYWORD_ROUTING_KEY);


        return new Declarables(
                crawlerExchange,
                keywordQueue,
                keywordBinding
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
