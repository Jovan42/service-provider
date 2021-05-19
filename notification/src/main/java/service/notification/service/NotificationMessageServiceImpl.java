package service.notification.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import service.notification.dto.Message;

@Service
public class NotificationMessageServiceImpl implements NotificationMessageService {
    private SimpMessagingTemplate simpMessagingTemplate;

    public NotificationMessageServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendMessage(Message message) {
        simpMessagingTemplate.convertAndSend("/topic/order", message);
    }
}
