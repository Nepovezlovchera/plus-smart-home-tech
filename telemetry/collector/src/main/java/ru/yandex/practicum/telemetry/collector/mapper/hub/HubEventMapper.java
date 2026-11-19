package ru.yandex.practicum.telemetry.collector.mapper.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.telemetry.collector.mapper.hub.handler.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HubEventMapper {

    private final Map<HubEventType, HubEventHandler> handlers;

    public HubEventMapper(List<HubEventHandler> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(HubEventHandler::getType, Function.identity()));
    }

    public HubEventAvro toAvro(HubEvent event) {
        HubEventHandler handler = handlers.get(event.getType());
        if (handler == null) {
            throw new IllegalArgumentException("Нет обработчика для типа " + event.getType());
        }
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(handler.convert(event))
                .build();
    }
}