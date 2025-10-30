package ru.yandex.practicum.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.config.KafkaAvroProducer;
import ru.yandex.practicum.collector.dto.hub.HubEvent;
import ru.yandex.practicum.collector.mapper.HubEventMapper;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Slf4j
@RequiredArgsConstructor
@Service
public final class HubEventService {

    private final KafkaAvroProducer producer;

    private final Producer<String, SpecificRecord> hubProducer;

    @Value("${spring.kafka.hub-topic:telemetry.hubs.v1}")
    private String hubTopic;

    public void sendEvent(final HubEvent dto) {
        HubEventAvro avro = HubEventMapper.toAvro(dto);
        String key = avro.getHubId();

        hubProducer.send(new ProducerRecord<>(hubTopic, key, avro),
                (meta, ex) -> {
            if (ex != null) {
                log.error("Failed to send hub event {} to Kafka", avro, ex);
            } else {
                log.info("Sent huv event to {} partition={} offset={}",
                        meta.topic(), meta.partition(), meta.offset());
            }
        });
    }

    public void close() {
        hubProducer.flush();
        hubProducer.close();
    }
}

