#!/bin/sh



is_container_running() {
    docker ps --format '{{.Names}}' | grep -q "^$1$"
}

CONTAINER_NAME="adm_videos_mysql"

if is_container_running $CONTAINER_NAME; then
    echo "Container $CONTAINER_NAME already running"
else
    echo "Container $CONTAINER_NAME is not running. Starting docker-compose up..."
    docker-compose up -d 
fi

sleep 1

docker ps 

sleep 1

./gradlew :admin-catalog:bootRun

# java -jar admin-catalog/build/libs/application.jar 