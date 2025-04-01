package ru.prytkovv.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.Session;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.session.web.socket.server.SessionRepositoryMessageInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;


@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    private final String topic;
    private final Long heartbeatIncomingMs;
    private final Long heartbeatOutgoingMs;
    private final Integer maxMessageSizeBytes;
    private final Integer maxBufferSizeBytes;
    private final Integer maxSendTimeLimitMs;
    private final SessionRepositoryMessageInterceptor<Session> sessionRepositoryMessageInterceptor;
    private TaskScheduler taskScheduler;

    public WebSocketConfig(@Value("${app.websocket.topic}") String topic,
                           @Value("${app.websocket.heartbeat.incoming-ms}") Long heartbeatIncomingMs,
                           @Value("${app.websocket.heartbeat.outgoing-ms}") Long heartbeatOutgoingMs,
                           @Value("${app.websocket.max-message-size-bytes}") Integer maxMessageSizeBytes,
                           @Value("${app.websocket.max-buffer-size-bytes}") Integer maxBufferSizeBytes,
                           @Value("${app.websocket.max-send-time-limit-ms}") Integer maxSendTimeLimitMs,
                           SessionRepositoryMessageInterceptor<Session> sessionRepositoryMessageInterceptor) {
        this.topic = topic;
        this.heartbeatIncomingMs = heartbeatIncomingMs;
        this.heartbeatOutgoingMs = heartbeatOutgoingMs;
        this.maxMessageSizeBytes = maxMessageSizeBytes;
        this.maxBufferSizeBytes = maxBufferSizeBytes;
        this.maxSendTimeLimitMs = maxSendTimeLimitMs;
        this.sessionRepositoryMessageInterceptor = sessionRepositoryMessageInterceptor;
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
    public void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws/products").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        super.configureClientInboundChannel(registration);
        registration.interceptors(sessionRepositoryMessageInterceptor);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(maxMessageSizeBytes);
        registry.setSendBufferSizeLimit(maxBufferSizeBytes);
        registry.setSendTimeLimit(maxSendTimeLimitMs);
    }

}
