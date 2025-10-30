package ru.yandex.practicum.collector.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.collector.dto.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@NoArgsConstructor
public class SensorEventMapper {

    public static SensorEventAvro toAvro(SensorEvent dto) {
        SensorEventAvro.Builder builder = SensorEventAvro.newBuilder()
                .setId(dto.getId())
                .setHubId(dto.getHubId())
                .setTimestamp(dto.getTimestamp().toEpochMilli());

        switch (dto.getType()) {
            case LIGHT_SENSOR_EVENT -> {
                LightSensorEvent d = (LightSensorEvent) dto;
                LightSensorAvro payload = LightSensorAvro.newBuilder()
                        .setLinkQuality(d.getLinkQuality())
                        .setLuminosity(d.getLuminosity())
                        .build();
                builder.setPayload(payload);
            }
            case TEMPERATURE_SENSOR_EVENT -> {
                TemperatureSensorEvent d = (TemperatureSensorEvent) dto;
                TemperatureSensorAvro payload = TemperatureSensorAvro.newBuilder()
                        .setTemperatureC(d.getTemperatureC())
                        .setTemperatureF(d.getTemperatureF())
                        .build();
                builder.setPayload(payload);
            }
            case MOTION_SENSOR_EVENT -> {
                MotionSensorEvent d = (MotionSensorEvent) dto;
                MotionSensorAvro payload = MotionSensorAvro.newBuilder()
                        .setLinkQuality(d.getLinkQuality())
                        .setMotion(d.getMotion())
                        .setVoltage(d.getVoltage())
                        .build();
                builder.setPayload(payload);
            }
            case SWITCH_SENSOR_EVENT -> {
                SwitchSensorEvent d = (SwitchSensorEvent) dto;
                SwitchSensorAvro payload = SwitchSensorAvro.newBuilder()
                        .setState(d.getState())
                        .build();
                builder.setPayload(payload);
            }
            case CLIMATE_SENSOR_EVENT -> {
                ClimateSensorEvent d = (ClimateSensorEvent) dto;
                ClimateSensorAvro payload = ClimateSensorAvro.newBuilder()
                        .setCo2Level(d.getCo2Level())
                        .setHumidity(d.getHumidity())
                        .setTemperatureC(d.getTemperatureC())
                        .build();
                builder.setPayload(payload);
            }
            default -> throw new IllegalArgumentException("Unknown sensor event type: " + dto.getType());
        }

        return builder.build();
    }
}