package ru.practicum.dto.sensor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
// 1. @JsonTypeInfo: Инструктируем Jackson, что это полиморфный класс.
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,              // Используем строковое имя (значение поля 'type')
        include = JsonTypeInfo.As.EXISTING_PROPERTY, // Тип определяется по полю в JSON
        property = "type", // Имя поля, которое содержит тип ('type')
        defaultImpl = SensorEventType.class
)
// 2. @JsonSubTypes: Перечисляем все возможные дочерние классы.
//    Значение 'name' должно точно совпадать со строками в спецификации (ENUM-ы).
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClimateSensorEvent.class, name = "CLIMATE_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = LightSensorEvent.class, name = "LIGHT_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = MotionSensorEvent.class, name = "MOTION_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = SwitchSensorEvent.class, name = "SWITCH_SENSOR_EVENT"),
        @JsonSubTypes.Type(value = TemperatureSensorEvent.class, name = "TEMPERATURE_SENSOR_EVENT")
})
@Getter @Setter @ToString
public abstract class SensorEvent {
    @NotBlank
    private String id;
    @NotBlank
    private String hubId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant timestamp=Instant.now();

    @NotNull
    public abstract SensorEventType getType();
}
