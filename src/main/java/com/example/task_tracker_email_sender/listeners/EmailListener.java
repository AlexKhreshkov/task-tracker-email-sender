package com.example.task_tracker_email_sender.listeners;

import com.example.task_tracker_email_sender.events.UserCreatedEvent;
import com.example.task_tracker_email_sender.services.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailListener {

    private final EmailSenderService emailSenderService;

    public EmailListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @KafkaListener(
            topics = "EMAIL_SENDING_TASKS"
    )
    public void handleEmailMessage(UserCreatedEvent userCreatedEvent) {
        log.info("Received event for email: {}", userCreatedEvent.getEmail());
        this.emailSenderService.sendEmail(userCreatedEvent.getEmail(), userCreatedEvent.getTitle(), userCreatedEvent.getText());
    }
}
