package ru.yandex.practicum.telemetry.collector.mapper.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.mapper.sensor.handler.SensorEventHandler;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SensorEventMapper {

    private final Map<SensorEventType, SensorEventHandler> handlers;

    public SensorEventMapper(List<SensorEventHandler> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(SensorEventHandler::getType, Function.identity()));
    }

    public SensorEventAvro toAvro(SensorEvent event) {
        SensorEventHandler handler = handlers.get(event.getType());
        if (handler == null) {
            throw new IllegalArgumentException("Нет обработчика для типа " + event.getType());
        }
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(handler.convert(event))
                .build();
    }
}
