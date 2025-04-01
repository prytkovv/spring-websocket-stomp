package ru.prytkovv.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.socket.server.SessionRepositoryMessageInterceptor;

import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@EnableSpringHttpSession
public class SessionRepositoryConfig {

    private final Duration maxInactiveInterval;

    public SessionRepositoryConfig(@Value("${app.session.max-inactive-interval}") Duration maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Bean
    public SessionRepository<?> sessionRepository() {
        ConcurrentHashMap<String, Session> map = new ConcurrentHashMap<>();
        MapSessionRepository sessionRepository = new MapSessionRepository(map);
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveInterval);
        return sessionRepository;
    }

    @Bean
    public SessionRepositoryMessageInterceptor<?> sessionRepositoryMessageInterceptor(SessionRepository<?> sessionRepository) {
        Set<SimpMessageType> matchingMessageTypes = EnumSet.of(SimpMessageType.CONNECT,
                                                               SimpMessageType.MESSAGE,
                                                               SimpMessageType.SUBSCRIBE,
                                                               SimpMessageType.UNSUBSCRIBE,
                                                               SimpMessageType.HEARTBEAT);
        SessionRepositoryMessageInterceptor<?> interceptor = new SessionRepositoryMessageInterceptor<>(sessionRepository);
        interceptor.setMatchingMessageTypes(matchingMessageTypes);
        return interceptor;
    }
}
