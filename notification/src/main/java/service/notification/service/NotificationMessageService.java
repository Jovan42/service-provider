package service.notification.service;

import org.springframework.stereotype.Service;
import service.notification.dto.MessageBody;

import java.util.List;

@Service
public interface NotificationMessageService {
    void sendNotification(String destination, MessageBody messageBody);
    void sendNotifications(List<String> destinations, MessageBody messageBody);
    void sendMail(MessageBody messageBody);
    void sendSMS(MessageBody messageBody);
}
