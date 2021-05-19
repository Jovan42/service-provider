package service.notification.service;

import org.springframework.stereotype.Service;
import service.notification.dto.Message;

@Service
public interface NotificationMessageService {
    void sendNotification(Message message);
    void sendMail(Message message);
}
