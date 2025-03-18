#!/bin/bash

URL="ws://localhost:8080/api/ws/products"
DESTINATION="/"
HEARTBEAT="0,30000"

{
    echo -e "CONNECT\naccept-version:1.0,1.1,1.2\nheart-beat:$HEARTBEAT\n\n\x00"

    echo -e "SUBSCRIBE\ndestination:$DESTINATION\nid:sub-0\n\n\x00"

    while true; do
        read -r message
        if [ -n "$message" ]; then
            echo "Received: $message"
        fi
    done
} | websocat --protocol stomp $URL

