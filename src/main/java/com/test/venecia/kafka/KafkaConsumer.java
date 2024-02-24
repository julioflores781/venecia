package com.test.venecia.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Profile("!github-actions")
public class KafkaConsumer {

    @KafkaListener(topics = "TOPIC", groupId = "group_id_" + "${random.uuid}")
    public void consume(String message, Acknowledgment acknowledgment) {
        System.out.println("Mensaje recibido de Kafka: " + message);
        // Procesar el mensaje...
        acknowledgment.acknowledge(); // Confirmar el offset manualmente
    }
}
