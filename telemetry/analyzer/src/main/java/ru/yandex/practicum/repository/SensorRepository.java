package ru.yandex.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.entity.HomeSensor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
public interface SensorRepository extends JpaRepository<HomeSensor, Long> {
    Optional<HomeSensor> findByIdAndHubId(String id, String hubId);

    void deleteById(String id);

    List<HomeSensor> findAllByIdIn(Collection<String> ids);
}