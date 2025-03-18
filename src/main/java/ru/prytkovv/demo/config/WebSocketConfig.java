package ru.prytkovv.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;


@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final String topic;
    private final Long heartbeatIncomingMs;
    private final Long heartbeatOutgoingMs;
    private final Integer maxMessageSizeBytes;
    private final Integer maxBufferSizeBytes;
    private final Integer maxSendTimeLimitMs;
    private TaskScheduler taskScheduler;

    public WebSocketConfig(@Value("${app.websocket.topic}") String topic,
                           @Value("${app.websocket.heartbeat.incoming-ms}") Long heartbeatIncomingMs,
                           @Value("${app.websocket.heartbeat.outgoing-ms}") Long heartbeatOutgoingMs,
                           @Value("${app.websocket.max-message-size-bytes}") Integer maxMessageSizeBytes,
                           @Value("${app.websocket.max-buffer-size-bytes}") Integer maxBufferSizeBytes,
                           @Value("${app.websocket.max-send-time-limit-ms}") Integer maxSendTimeLimitMs) {
        this.topic = topic;
        this.heartbeatIncomingMs = heartbeatIncomingMs;
        this.heartbeatOutgoingMs = heartbeatOutgoingMs;
        this.maxMessageSizeBytes = maxMessageSizeBytes;
        this.maxBufferSizeBytes = maxBufferSizeBytes;
        this.maxSendTimeLimitMs = maxSendTimeLimitMs;
    }

    @Autowired
    public void setTaskScheduler(@Lazy TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config
                .enableSimpleBroker(topic)
                .setHeartbeatValue(new long[] {heartbeatOutgoingMs, heartbeatIncomingMs})
                .setTaskScheduler(taskScheduler);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws/products").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(maxMessageSizeBytes);
        registry.setSendBufferSizeLimit(maxBufferSizeBytes);
        registry.setSendTimeLimit(maxSendTimeLimitMs);
    }
}
