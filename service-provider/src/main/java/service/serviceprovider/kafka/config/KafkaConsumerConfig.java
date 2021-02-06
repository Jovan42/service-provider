package service.serviceprovider.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import service.sharedlib.ErrorEnums;
import service.sharedlib.events.BaseEvent;
import service.sharedlib.events.OrderRequestDeclinedEvent;
import service.sharedlib.exceptions.InvalidOrderException;
import service.sharedlib.exceptions.enums.OrderInvalidReason;

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groupId}")
    private String groupId;

    public KafkaConsumerConfig(KafkaTemplate<String, BaseEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public ConsumerFactory<String, BaseEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new JsonDeserializer<>(BaseEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BaseEvent>
            kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, BaseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
//        factory.setErrorHandler(
//                (e, consumerRecord) -> {
//                    if (e.getCause() instanceof InvalidOrderException) {
//                        InvalidOrderException invalidOrderException = (InvalidOrderException) e.getCause();
//                        kafkaTemplate.send(
//                                "orderTopic",
//                                "",
//                                OrderRequestDeclinedEvent.builder()
//                                        .orderId(invalidOrderException.getOrderId())
//                                        .reason(
//                                                invalidOrderException.getReason())
//                                        .build());
//                    }
//                });
        return factory;
    }

}
