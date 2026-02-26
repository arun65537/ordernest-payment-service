package com.ordernest.payment.messaging;

import com.ordernest.payment.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${app.kafka.topic.payment-events}")
    private String paymentEventsTopic;

    public void publish(PaymentEvent paymentEvent) {

        kafkaTemplate.send(paymentEventsTopic, paymentEvent.orderId(), paymentEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish payment event for orderId={}",
                                paymentEvent.orderId(), ex);
                    } else {
                        log.info("Payment event published successfully. topic={}, partition={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });

        // Ensures message is sent before request completes
        kafkaTemplate.flush();
    }
}