package service.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.notification.dto.Message;
import service.notification.dto.OrderResponse;

@Service
public class NotificationMessageServiceImpl implements NotificationMessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JavaMailSender emailSender;
    private final RestTemplate restTemplate;

    public NotificationMessageServiceImpl(
            SimpMessagingTemplate simpMessagingTemplate,
            JavaMailSender emailSender,
            RestTemplate restTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.emailSender = emailSender;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNotification(Message message) {
        simpMessagingTemplate.convertAndSend(   "/topic/order", message);
    }

    @Override
    public void sendMail(Message message) {
        OrderResponse orderInfo = restTemplate.getForObject("http://localhost:8090/orders/1", OrderResponse.class);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("jovan0042@gmail.com");
        //        message.setTo(employee.getEmail());
        simpleMailMessage.setSubject(message.getEventName());
        simpleMailMessage.setText(message.getMessage());
        emailSender.send(simpleMailMessage);
    }
}
