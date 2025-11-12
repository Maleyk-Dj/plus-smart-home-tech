package ru.yandex.practicum.collector.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.collector.kafka.CollectorSerializer;
import ru.yandex.practicum.collector.kafka.EventProducer;

import java.util.Properties;

@Slf4j
@Configuration
public class EventProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public EventProducer getEventProducer() {
        return new EventProducer() {
            @Override
            public Producer<String, SpecificRecordBase> getProducer() {
                Properties config = new Properties();
                config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
                config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Прямой класс
                config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CollectorSerializer.class); // Прямой класс
                config.put(ProducerConfig.ACKS_CONFIG, "all");
                config.put(ProducerConfig.RETRIES_CONFIG, 3);
                config.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
                config.put(ProducerConfig.LINGER_MS_CONFIG, 1);
                config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

                config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000);
                config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);

                log.info("✅ Kafka Producer configuration completed");


                return new KafkaProducer<>(config);
            }
        };
    }
}