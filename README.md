# Java Email API

This is a simple Java library (API) for sending emails using SMTP. It uses the modern Jakarta Mail library.

## Project Structure

- **src/main/java/com/emailapi/model/EmailRequest.java**: Data class for email details (to, subject, body, attachments).
- **src/main/java/com/emailapi/service/EmailService.java**: Interface defining the API.
- **src/main/java/com/emailapi/service/SmtpEmailService.java**: Implementation using SMTP.
- **src/main/java/com/emailapi/Main.java**: Example usage file.

## Prerequisites

- Java 17 or higher
- Maven (or an IDE like IntelliJ IDEA, Eclipse, or VS Code)
- An SMTP account (e.g., Gmail, Outlook, Mailtrap)

## Configuration

In `src/main/java/com/emailapi/Main.java`, update the following variables with your credentials:

```java
String host = "smtp.gmail.com";
int port = 587;
String username = "your-email@gmail.com";
String password = "your-app-password"; // Use App Password for Gmail
```

**Note for Gmail Users:**
If you use Gmail, you likely need to enable 2-Step Verification and generate an **App Password**. You cannot use your regular login password.

## How to Run (Web API Server)

This is now a **Spring Boot Web Application**. It runs a server that listens for email requests.

### Configuration
1. Open `src/main/resources/application.properties`.
2. Ensure your Gmail credentials (`spring.mail.username`, `spring.mail.password`) are correct.
3. **Important:** Change the `app.api-key` to a secure random string. This is your password to use the API.

### Running the Server
**Using VS Code:**
1. Open `src/main/java/com/emailapi/Main.java`.
2. Click "Run" or press F5.
3. The server will start on `localhost:8080`.

### sending an Email (Testing)
You can use **Postman**, **curl**, or any HTTP client to send a request.

**Example using cURL:**
```bash
curl -X POST http://localhost:8080/api/send-email \
  -H "Content-Type: application/json" \
  -H "x-api-key: my-secret-api-key-12345" \
  -d '{
    "to": "recipient@example.com",
    "subject": "Hello via API",
    "body": "This is a secured email sent from my local server!",
    "html": false,
    "replyTo": "contact@example.com"
  }'
```

**Security:**
- The API requires the header `x-api-key`.
- If the key is missing or wrong, the server returns `401 Unauthorized`.
