package com.api.moments.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {
  @Autowired
  private KafkaTemplate<String, String> _kafka;

  private String topic;

  @Override
  public void send(String topic, String event) {
    _kafka.send(topic, event);
  }

  @KafkaListener(topics = "new-post", groupId = "ms-post")
  @Override
  public void consume(ConsumerRecord<String, String> event) {
    System.out.println("Event consumed: " + event.value());
  }
}
