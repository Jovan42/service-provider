package service.serviceprovider.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;
import service.sharedlib.exceptions.InvalidOrderException;

public class KafkaErrorHandler implements ErrorHandler {


    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
        System.out.println(e);
        if( e.getCause().getCause() instanceof InvalidOrderException) {

        }


    }
}
