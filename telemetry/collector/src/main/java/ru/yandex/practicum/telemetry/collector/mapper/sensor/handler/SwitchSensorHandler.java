package ru.yandex.practicum.telemetry.collector.mapper.sensor.handler;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.telemetry.collector.model.sensor.SwitchSensorEvent;

@Component
public class SwitchSensorHandler implements SensorEventHandler {

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SpecificRecordBase convert(SensorEvent event) {
        SwitchSensorEvent e = (SwitchSensorEvent) event;
        return SwitchSensorAvro.newBuilder()
                .setState(e.isState())
                .build();
    }
}