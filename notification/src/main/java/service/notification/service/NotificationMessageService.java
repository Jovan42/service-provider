package service.notification.service;

import org.springframework.stereotype.Service;
import service.notification.dto.Message;

@Service
public interface NotificationMessageService {
    void sendMessage(Message message);
}
