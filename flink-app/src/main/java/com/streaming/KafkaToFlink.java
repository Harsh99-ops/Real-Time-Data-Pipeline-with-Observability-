package com.streaming;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KafkaToFlink {

    public static void main(String[] args) throws Exception {

        // Create the Flink execution environment
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        // Create Kafka source
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(
                        "kafka1:9092,kafka2:9092,kafka3:9092"
                )
                .setTopics("transactions")
                .setGroupId("flink-consumer")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        // Read data from Kafka
        DataStream<String> transactions = env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "Kafka Transactions"
        );

        // Print every transaction
        transactions.print();

        // Start the Flink job
        env.execute("Kafka To Flink");
    }
}
