package com.emailapi.service;

import com.emailapi.model.EmailRequest;

public interface EmailService {
    void sendEmail(EmailRequest emailRequest);
}
