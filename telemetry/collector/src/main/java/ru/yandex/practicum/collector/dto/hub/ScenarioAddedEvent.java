package ru.yandex.practicum.collector.dto.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.yandex.practicum.collector.dto.hub.action.DeviceAction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.yandex.practicum.collector.dto.hub.scenario.ScenarioCondition;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioAddedEvent extends HubEvent {
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotNull
    @Valid
    private List<ScenarioCondition> conditions;
    @NotNull
    @Valid
    private List<DeviceAction> actions;


    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
