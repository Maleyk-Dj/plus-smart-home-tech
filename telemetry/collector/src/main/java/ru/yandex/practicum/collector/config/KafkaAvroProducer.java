package ru.yandex.practicum.collector.config;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.collector.serializer.AvroSerializer;

import java.util.Properties;

@Configuration
public class KafkaAvroProducer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Properties baseProps() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32_768);
        return props;
    }

    @Bean
    public Producer<String, SpecificRecord> sensorProducer() {
        return new KafkaProducer<>(baseProps());
    }

    @Bean
    public Producer<String, SpecificRecord> hubProducer() {
        return new KafkaProducer<>(baseProps());
    }
}



