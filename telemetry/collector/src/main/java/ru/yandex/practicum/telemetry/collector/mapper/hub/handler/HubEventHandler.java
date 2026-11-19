package ru.yandex.practicum.telemetry.collector.mapper.hub.handler;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEventType;

public interface HubEventHandler {
    HubEventType getType();

    SpecificRecordBase convert(HubEvent event);
}