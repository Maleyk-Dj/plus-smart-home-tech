package ru.yandex.practicum.collector.grpc;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.collector.handler.hub.HubEventHandler;
import ru.yandex.practicum.collector.handler.sensor.SensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {

    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;

    // Убедитесь, что конструктор правильный
    @Autowired
    public EventController(Set<SensorEventHandler> sensorEventHandlers,
                           Set<HubEventHandler> hubEventHandlers) {
        log.info("🔧 Initializing EventController with {} sensor handlers and {} hub handlers",
                sensorEventHandlers.size(), hubEventHandlers.size());

        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(
                        SensorEventHandler::getMessageType,
                        Function.identity()
                ));
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(
                        HubEventHandler::getMessageType,
                        Function.identity()
                ));

        log.info("✅ Registered sensor handlers: {}", this.sensorEventHandlers.keySet());
        log.info("✅ Registered hub handlers: {}", this.hubEventHandlers.keySet());
    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        log.info("📡 Received HUB event from hub: {}, type: {}", request.getHubId(), request.getPayloadCase());

        try {
            HubEventProto.PayloadCase payloadCase = request.getPayloadCase();

            if (hubEventHandlers.containsKey(payloadCase)) {
                log.debug("Found handler for hub event type: {}", payloadCase);
                hubEventHandlers.get(payloadCase).handle(request);
                log.info("✅ Successfully processed HUB event for hub: {}", request.getHubId());

                responseObserver.onNext(Empty.getDefaultInstance());
                responseObserver.onCompleted();
            } else {
                log.warn("❌ No handler found for hub event type: {}", payloadCase);
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("No handler for event type: " + payloadCase)
                        .asRuntimeException());
            }
        } catch (Exception e) {
            log.error("💥 ERROR processing HUB event for hub: {}", request.getHubId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error processing hub event: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        log.info("📡 Received SENSOR event from device: {}, hub: {}, type: {}",
                request.getId(), request.getHubId(), request.getPayloadCase());

        try {
            SensorEventProto.PayloadCase payloadCase = request.getPayloadCase();

            if (sensorEventHandlers.containsKey(payloadCase)) {
                log.debug("Found handler for sensor event type: {}", payloadCase);
                sensorEventHandlers.get(payloadCase).handle(request);
                log.info("✅ Successfully processed SENSOR event for device: {}", request.getId());

                responseObserver.onNext(Empty.getDefaultInstance());
                responseObserver.onCompleted();
            } else {
                log.warn("❌ No handler found for sensor event type: {}", payloadCase);
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("No handler for event type: " + payloadCase)
                        .asRuntimeException());
            }
        } catch (Exception e) {
            log.error("💥 ERROR processing SENSOR event for device: {}", request.getId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error processing sensor event: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}