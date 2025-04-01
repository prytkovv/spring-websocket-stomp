package ru.prytkovv.demo.messaging;


public interface MessagePublisher {

    void publish(byte[] data, String channel);

    void publish(String data, String channel);

    void publish(Object data, String channel);

    void publish(byte[] data);

    void publish(String data);

    void publish(Object data);
}
