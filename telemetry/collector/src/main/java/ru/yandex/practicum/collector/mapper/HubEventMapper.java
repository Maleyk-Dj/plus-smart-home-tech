package ru.yandex.practicum.collector.mapper;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.collector.dto.hub.*;
import ru.yandex.practicum.collector.dto.hub.action.DeviceAction;
import ru.yandex.practicum.collector.dto.hub.scenario.ScenarioCondition;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class HubEventMapper {


    public static HubEventAvro toAvro(HubEvent dto) {
        // общие поля
        HubEventAvro.Builder builder = HubEventAvro.newBuilder()
                .setHubId(dto.getHubId())
                .setTimestamp(dto.getTimestamp().toEpochMilli());

        switch (dto.getType()) {
            case DEVICE_ADDED: {
                DeviceAddedEvent ev = (DeviceAddedEvent) dto;
                DeviceAddedEventAvro payload = DeviceAddedEventAvro.newBuilder()
                        .setId(ev.getId())
                        .setDeviceType(DeviceTypeAvro.valueOf(ev.getDeviceType().name()))
                        .build();
                builder.setPayload(payload);
                break;
            }

            case DEVICE_REMOVED: {
                DeviceRemovedEvent ev = (DeviceRemovedEvent) dto;
                DeviceRemovedEventAvro payload = DeviceRemovedEventAvro.newBuilder()
                        .setId(ev.getId())
                        .build();
                builder.setPayload(payload);
                break;
            }

            case SCENARIO_ADDED: {
                ScenarioAddedEvent ev = (ScenarioAddedEvent) dto;

                // conditions
                List<ScenarioConditionAvro> conditionAvros = new ArrayList<>();
                if (ev.getConditions() != null) {
                    for (ScenarioCondition condDto : ev.getConditions()) {
                        ScenarioConditionAvro.Builder condB = ScenarioConditionAvro.newBuilder()
                                .setSensorId(condDto.getSensorId())
                                .setType(ConditionTypeAvro.valueOf(condDto.getType().name()))
                                .setOperation(ConditionOperationAvro.valueOf(condDto.getOperation().name()));

                        Object val = condDto.getValue();
                        if (val == null) {
                            condB.setValue(null);
                        } else if (val instanceof Boolean) {
                            // если DTO хранит boolean
                            condB.setValue((Boolean) val);
                        } else if (val instanceof Integer) {
                            condB.setValue((Integer) val);
                        } else if (val instanceof Long) {
                            condB.setValue(((Long) val).intValue());
                        } else {
                            throw new IllegalArgumentException("Unsupported ScenarioCondition.value type: "
                                    + val.getClass());
                        }

                        conditionAvros.add(condB.build());
                    }
                }

                List<DeviceActionAvro> actionAvros = new ArrayList<>();
                if (ev.getActions() != null) {
                    for (DeviceAction actionDto : ev.getActions()) {
                        DeviceActionAvro.Builder actB = DeviceActionAvro.newBuilder()
                                .setSensorId(actionDto.getSensorId())
                                .setType(ActionTypeAvro.valueOf(actionDto.getType().name()));

                        Integer actionValue = actionDto.getValue();
                        if (actionValue == null) {
                            actB.setValue(null);
                        } else {
                            actB.setValue(actionValue);
                        }

                        actionAvros.add(actB.build());
                    }
                }
                ScenarioAddedEventAvro payload = ScenarioAddedEventAvro.newBuilder()
                        .setName(ev.getName())
                        .setConditions(conditionAvros)
                        .setActions(actionAvros)
                        .build();

                builder.setPayload(payload);
                break;
            }
            case SCENARIO_REMOVED: {
                ScenarioRemovedEvent ev = (ScenarioRemovedEvent) dto;
                ScenarioRemovedEventAvro payload = ScenarioRemovedEventAvro.newBuilder()
                        .setName(ev.getName())
                        .build();
                builder.setPayload(payload);
                break;
            }

            default:
                throw new IllegalArgumentException("Unknown HubEvent type: " + dto.getType());
        }
        return builder.build();
    }
}
