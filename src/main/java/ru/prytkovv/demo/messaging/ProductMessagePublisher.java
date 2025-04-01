package ru.prytkovv.demo.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.prytkovv.demo.dto.ProductDto;


@Slf4j
@Service
public class ProductMessagePublisher extends SimpMessagePublisher {

    private final String topic;

    public ProductMessagePublisher(SimpMessageSendingOperations template,
                                   @Value("${app.websocket.topic}") String topic) {
        super(template);
        this.topic = topic;
    }

    public void publish(ProductDto product) {
        publish(product, topic);
    }

}
