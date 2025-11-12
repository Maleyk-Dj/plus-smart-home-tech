package ru.yandex.practicum.collector.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
@Component
@Slf4j
public class CollectorSerializer implements Serializer<SpecificRecordBase> {
    private final EncoderFactory encoderFactory = EncoderFactory.get();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        log.debug("🔧 Configuring CollectorSerializer");
    }

    @Override
    public byte[] serialize(String topic, SpecificRecordBase data) {
        if (data == null) {
            log.warn("⚠️ Attempted to serialize null data for topic: {}", topic);
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            log.debug("🔄 Serializing data for topic: {}, schema: {}", topic, data.getSchema().getName());

            BinaryEncoder encoder = encoderFactory.binaryEncoder(outputStream, null);
            DatumWriter<SpecificRecordBase> datumWriter = new SpecificDatumWriter<>(data.getSchema());
            datumWriter.write(data, encoder);
            encoder.flush();

            byte[] result = outputStream.toByteArray();
            log.debug("✅ Serialized {} bytes for topic: {}", result.length, topic);
            return result;

        } catch (IOException e) {
            log.error("💥 Serialization error for topic: {}", topic, e);
            throw new SerializationException("Ошибка сериализации данных для топика: " + topic, e);
        }
    }

    @Override
    public void close() {
        log.debug("🔧 Closing CollectorSerializer");
    }
}
