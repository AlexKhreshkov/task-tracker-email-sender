package com.example.task_tracker_email_sender.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderService {

    public void sendEmail(String email, String subject, String body) {
        log.info("Send email to: {}", email);
    }

}
