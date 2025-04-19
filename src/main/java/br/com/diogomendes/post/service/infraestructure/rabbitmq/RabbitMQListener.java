package br.com.diogomendes.post.service.infraestructure.rabbitmq;

import br.com.diogomendes.post.service.api.model.PostOutput;
import br.com.diogomendes.post.service.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static br.com.diogomendes.post.service.infraestructure.rabbitmq.RabbitMQConfig.QUEUE_POST_SERVICE;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final PostService service;

    @RabbitListener(queues = QUEUE_POST_SERVICE )
    @SneakyThrows
    public void handlePost(@Payload PostOutput postOutput) {
        Thread.sleep(Duration.ofSeconds(10));
        log.info("Received post: {}", postOutput);
        service.createPost(postOutput);
    }

}
