package ru.prytkovv.demo.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;


@Slf4j
public class SimpMessagePublisher implements MessagePublisher {

    private final SimpMessageSendingOperations template;

    public SimpMessagePublisher(SimpMessageSendingOperations template) {
        this.template = template;
    }

    @Override
    public void publish(byte[] data, String channel) {
        template.convertAndSend(channel, data);
        log.debug("Message was send to " + data);
    }

    @Override
    public void publish(String data, String channel) {
        template.convertAndSend(channel, data);
        log.debug("Message was send to " + data);
    }

    @Override
    public void publish(Object data, String channel) {
        template.convertAndSend(channel, data);
        log.debug("Message was send to " + data);
    }

    @Override
    public void publish(byte[] data) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public void publish(String data) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public void publish(Object data) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
