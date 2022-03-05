package service.notification.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.notification.dto.MessageBody;

import java.util.List;

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

    //    @Value("#{systemEnvironment['TWILIO_ACCOUNT_SID']}")
    private String ACCOUNT_SID = "ACf48ef8a113186be522885d6923dcd8e6";

    //    @Value("#{systemEnvironment['TWILIO_AUTH_TOKEN']}")
    private String AUTH_TOKEN = "20b314096c147e803fd9e442c9abdeea";

    //    @Value("#{systemEnvironment['TWILIO_PHONE_NUMBER']}")
    private String FROM_NUMBER = "+14792412093";

    @Override
    public void sendNotification(String destination, MessageBody messageBody) {
        simpMessagingTemplate.convertAndSend(destination, messageBody);
    }

    @Override
    public void sendNotifications(List<String> destinations, MessageBody messageBody) {
        destinations.forEach(
                destination -> simpMessagingTemplate.convertAndSend(destination, messageBody));
    }

    @Override
    public void sendMail(MessageBody messageBody) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(messageBody.getEmailAddress());
        simpleMailMessage.setSubject(messageBody.getEventName());
        simpleMailMessage.setText(messageBody.getMessage());
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendSMS(MessageBody messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber(messageBody.getPhoneNumber()),
                        new PhoneNumber(FROM_NUMBER),
                        messageBody.getMessage())
                .create();
    }
}
