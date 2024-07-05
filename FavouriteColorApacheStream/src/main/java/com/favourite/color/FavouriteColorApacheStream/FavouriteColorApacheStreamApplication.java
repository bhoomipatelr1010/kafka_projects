package com.favourite.color.FavouriteColorApacheStream;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FavouriteColorApacheStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavouriteColorApacheStreamApplication.class, args);
		testKafkaForFavColor();
	}

	private static void testKafkaForFavColor() {
		Properties properties = new Properties();
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "fav-color");
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");
		
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> textLines = builder.stream("favourite-color-input");

		KStream<String, String> usrAndColors = textLines.filter((k, v) -> v.contains(","))
				.selectKey((k, v) -> v.split(",")[0].toLowerCase()).mapValues(v -> v.split(",")[1].toLowerCase())
				.filter((user, colour) -> Arrays.asList("green", "blue", "red").contains(colour));

		usrAndColors.to("user-keys-and-colors");

		KTable<String, String> usrAndColorsTable = builder.table("user-keys-and-colors");

		KTable<String, Long> favColors = usrAndColorsTable.groupBy((user, color) -> new KeyValue<>(color, color))
				.count(Materialized.as("fav-colors"));
		favColors.toStream().to("favourite-color-output");

		KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), properties);
		kafkaStreams.cleanUp();
		kafkaStreams.start();

		System.out.println(kafkaStreams.toString());

		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
	}

}
