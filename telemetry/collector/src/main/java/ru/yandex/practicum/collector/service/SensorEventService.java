package ru.yandex.practicum.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.collector.dto.sensor.SensorEvent;
import ru.yandex.practicum.collector.mapper.SensorEventMapper;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Slf4j
@Service
@RequiredArgsConstructor
public final class SensorEventService {

    private final Producer<String, SpecificRecord> sensorProducer;

    @Value("${spring.kafka.sensor-topic:telemetry.sensors.v1}")
    private String sensorTopic;

    public void sendEvent(final SensorEvent dto) {
        SensorEventAvro avro = SensorEventMapper.toAvro(dto);
        String key = avro.getId();

        sensorProducer.send(new ProducerRecord<>(sensorTopic, key, avro),
                (meta, ex) -> {
            if (ex != null) {
                log.error("Failed to send sensor event {} to Kafka", avro, ex);
            } else {
                log.info("Sent sensor event to {} partition={} offset={}",
                        meta.topic(), meta.partition(),
                        meta.offset());
            }
        });
    }

    public void close() {
        sensorProducer.flush();
        sensorProducer.close();
    }
}