FROM flink:2.1.0

RUN wget -P /opt/flink/lib \
    https://repo.maven.apache.org/maven2/org/apache/flink/flink-connector-kafka/5.0.0-2.1/flink-connector-kafka-5.0.0-2.1.jar

RUN wget -P /opt/flink/lib \
    https://repo.maven.apache.org/maven2/org/apache/kafka/kafka-clients/4.2.0/kafka-clients-4.2.0.jar
