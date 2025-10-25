package ru.practicum.dto.hub;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.practicum.dto.action.DeviceAction;
import ru.practicum.dto.scenario.ScenarioCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Setter @Getter @ToString
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
    private List <ScenarioCondition> conditions;
    @NotNull
    @Valid
    private List <DeviceAction> actions;


    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
