package com.emailapi.controller;

import com.emailapi.model.EmailRequest;
import com.emailapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow requests from any website (Example: inventa.us)
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Value("${app.api-key}")
    private String validApiKey;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestHeader(value = "x-api-key", required = false) String apiKey,
            @RequestBody EmailRequest request) {

        // SECURITY CHECK
        if (apiKey == null || !apiKey.equals(validApiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Missing API Key");
        }

        try {
            // Note: The "From" address is often determined by the sender credentials in the
            // properties
            // So we might ignore request.getFrom() or use it as a display name if
            // supported.
            // For this implementation, the Service uses the configured username as sender.

            emailService.sendEmail(request);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending email: " + e.getMessage());
        }
    }
}
