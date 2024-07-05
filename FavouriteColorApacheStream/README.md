**This project is just to get info about how many users are having green, blue or red as fav color.**

**Some important command for kafka topic, producer and consumer**

1. kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic <topic_name>
2. kafka-topics.sh --bootstrap-server localhost:9092 --list
3. kafka-topics.sh --create --topic favourite-color-input --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
4. kafka-topics.sh --create --topic favourite-color-output --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --config cleanup.policy=compact
5. kafka-topics.sh --create --topic user-keys-and-colors --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1 --config cleanup.policy=compact
6. kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic favourite-color-output \
	--from-beginning \
    --property print.key=true \
	--property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
7. kafka-console-producer.sh --broker-list localhost:9092 --topic favourite-color-input
8. kafka-console-consumer.sh --bootstrap-server localhost:9092  --topic user-keys-and-colors --from-beginning --property print.key=true --property print.value=true  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

**Example for producer kafka topic value**
  1. user1,red
  2. user2,green
