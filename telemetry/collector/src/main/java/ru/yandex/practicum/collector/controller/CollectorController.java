package ru.yandex.practicum.collector.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.dto.hub.HubEvent;
import ru.yandex.practicum.collector.dto.sensor.SensorEvent;
import ru.yandex.practicum.collector.service.HubEventService;
import ru.yandex.practicum.collector.service.SensorEventService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class CollectorController {

    private final SensorEventService sensorEventService;
    private final HubEventService hubEventService;

    @PostMapping("/sensors")
    public ResponseEntity<Void> sensors(@Valid @RequestBody SensorEvent dto) {
        sensorEventService.sendEvent(dto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> hubs(@Valid @RequestBody HubEvent dto) {
        hubEventService.sendEvent(dto);
        return ResponseEntity.accepted().build();
    }
}

