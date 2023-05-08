package com.api.moments.services.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IEventService {
  void send(String topic, String event);

  void consume(ConsumerRecord<String, String> event);
}
