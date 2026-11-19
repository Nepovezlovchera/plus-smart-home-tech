package ru.yandex.practicum.telemetry.collector.mapper.sensor.handler;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;

public interface SensorEventHandler {
    SensorEventType getType();
    SpecificRecordBase convert(SensorEvent event);
}
