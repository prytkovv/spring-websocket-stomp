# Spring Websocket STOMP Example

## Overview

This project demonstrates how to integrate Spring Boot with WebSocket using [STOMP](https://stomp.github.io/) and [Spring Session](https://docs.spring.io/spring-session/reference/index.html) for real-time communication and session management.

## Features

- **WebSocket STOMP**: Utilizes WebSocket with STOMP for real-time, bi-directional communication.
- **Spring Session**: Manages user sessions using [MapSessionRepository](https://docs.spring.io/spring-session/docs/current/api/org/springframework/session/MapSessionRepository.html), which persists sessions in a Map where the key is the Session ID and the value is the Session object.
  > DISLAIMER FROM SPRING DOCS:
  > 
  > Note that the supplied map itself is responsible for purging the expired sessions.
  The implementation does NOT support firing SessionDeletedEvent or SessionExpiredEvent.
- **Session Expiration Management**: The [SessionRepositoryMessageInterceptor](https://docs.spring.io/spring-session/docs/current/api/org/springframework/session/web/socket/server/SessionRepositoryMessageInterceptor.html) updates the session's last accessed time with each incoming WebSocket message, and heartbeat messages keep the session alive.
- **Data Streaming**: The custom ProductService pushes new product data to connected clients every 5 seconds.
- **Spring Boot Integration**: Built with Spring Boot for easy configuration and deployment.

## Prerequisites

- Java 17 or later
- Python 3.10 or later
- A WebSocket-compatible client (e.g., Web browser or WebSocket testing tool)

## Installation

Build the Java project:
```
mvn clean install
```

## Running the Application

Start the application:
```
mvn spring-boot:run
```

## Usage

A typical client application might look like this. Be sure to use the same origin to preserve session cookies.
```
    const brokerURL = `ws://localhost:8080/api/ws/products`
    const client = new Client({
        brokerURL,
        reconnectDelay: 1000,
        heartbeatIncoming: 0,
        heartbeatOutgoing: 30000,
        debug: (str) => console.log(str),
        onConnect: () => {
            client.subscribe('/', message => console.log("Message: ", message))
        }
    })
```