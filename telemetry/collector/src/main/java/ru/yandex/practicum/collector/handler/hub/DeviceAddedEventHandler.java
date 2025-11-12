package ru.yandex.practicum.collector.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.mapper.Converter;
import ru.yandex.practicum.collector.kafka.CollectorTopics;
import ru.yandex.practicum.collector.kafka.EventProducer;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler implements HubEventHandler {
    private final EventProducer producer;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEventProto request) {
        log.info("🔄 Processing DEVICE_ADDED event for hub: {}", request.getHubId());

        try {
            DeviceAddedEventProto deviceAdded = request.getDeviceAdded();
            log.debug("Device ID: {}, Type: {}", deviceAdded.getId(), deviceAdded.getType());

            DeviceAddedEventAvro deviceAddedEventAvro = DeviceAddedEventAvro.newBuilder()
                    .setId(deviceAdded.getId())
                    .setDeviceType(Converter.mapToDeviceTypeAvro(deviceAdded.getType()))
                    .build();

            HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
                    .setHubId(request.getHubId())
                    .setTimestamp(request.getTimestamp().getSeconds() * 1000)
                    .setPayload(deviceAddedEventAvro)
                    .build();

            ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(
                    "telemetry.hubs.v1",
                    null,
                    System.currentTimeMillis(),
                    request.getHubId(),
                    hubEventAvro
            );

            producer.getProducer().send(record);
            log.info("✅ Successfully sent DEVICE_ADDED to Kafka for device: {}", deviceAdded.getId());

        } catch (Exception e) {
            log.error("💥 Error in DeviceAddedEventHandler for hub: {}", request.getHubId(), e);
            throw new RuntimeException("Failed to process device added event", e);
        }
    }
}
