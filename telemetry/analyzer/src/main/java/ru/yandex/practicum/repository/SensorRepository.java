package ru.yandex.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.entity.Sensor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByIdAndHubId(String id, String hubId);

    void deleteById(String id);

}