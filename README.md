Adding kafka related projects, will add information about all projects here


Docker compose file to run zookeeper and kafka

1. Create docker-compose.yaml
2. Add below details in this file.

          version: "3.1"
          services:
            zookeeper:
              image: zookeeper
              container_name: zookeeper
              ports:
                - "2181:2181"
            kafka:
              image: bitnami/kafka
              container_name: kafka
              ports:
                - "9092:9092"
              environment:
                KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
                KAFKA_ADVERTISED_HOST_NAME: localhost

4. Go to file path from cmd or terminal and run below command to get zookeeper and kafka up and running
    1. docker compose -f docker-compose.yaml up
    2. docker compose -f docker-compose.yaml up -d (to run in background)
    3. docker ps - to check running container
 
