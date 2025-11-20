package ru.yandex.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.entity.ScenarioAction;
import ru.yandex.practicum.entity.ScenarioActionId;


public interface ScenarioActionsRepository extends JpaRepository<ScenarioAction, ScenarioActionId> {
}
