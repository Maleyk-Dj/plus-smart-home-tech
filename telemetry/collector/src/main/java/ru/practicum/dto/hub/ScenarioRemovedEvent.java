package ru.practicum.dto.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioRemovedEvent extends HubEvent{
    @NotBlank
    @Size(min = 3)
    private String name;


    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
