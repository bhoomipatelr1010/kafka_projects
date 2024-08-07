
**How to run zookeeper and kafka using docker**
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
                KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092

4. Go to file path from cmd or terminal and run below command to get zookeeper and kafka up and running
    1. docker compose -f docker-compose.yaml up
    2. docker compose -f docker-compose.yaml up -d (to run in background)
    3. docker ps - to check running container

**How to create topic using kafka running in docker**

1. Execute below command to open kafka editor
   docker exec -it kafka /bin/sh
2. Go to /opt/kafka/bin file, location can be different of kafka file but it will be under opt folder
3. Run below command to create kafka topic 
   kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

 
