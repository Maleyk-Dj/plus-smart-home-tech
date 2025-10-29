package ru.yandex.practicum.collector.dto.hub.action;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private Integer value;
}
