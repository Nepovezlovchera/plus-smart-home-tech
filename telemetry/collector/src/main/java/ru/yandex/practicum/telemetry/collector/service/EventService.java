package ru.yandex.practicum.telemetry.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.collector.mapper.hub.HubEventMapper;
import ru.yandex.practicum.telemetry.collector.mapper.sensor.SensorEventMapper;
import ru.yandex.practicum.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.model.sensor.SensorEvent;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EventService {
    private final Producer<String, SpecificRecordBase> producer;
    private final SensorEventMapper sensorEventMapper;
    private final HubEventMapper hubEventMapper;

    @Value("${collector.kafka.topics.sensors}")
    private String sensorTopic;

    @Value("${collector.kafka.topics.hubs}")
    private String hubsTopic;

    private void send(String topic, String key, Instant timestamp, SpecificRecordBase value) {
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, null, timestamp.toEpochMilli(), key, value);
        producer.send(record, (metadata, exception) -> {
        });
    }

    public void collectSensorEvent(SensorEvent event) {
        send(sensorTopic, event.getHubId(), event.getTimestamp(), sensorEventMapper.toAvro(event));
    }

    public void collectHubEvent(HubEvent event) {
        send(hubsTopic, event.getHubId(), event.getTimestamp(), hubEventMapper.toAvro(event));
    }
}
