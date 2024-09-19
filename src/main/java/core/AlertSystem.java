package core;

//public class AlertSystem {
//    private static String spec = "http://localhost:3000/send-email";
//    public static void sendEmail(String recipient, String subject, String message) {
//        try {
//            // Define the URL of the email service
//            URL url = new URL(spec); // Replace with the actual URL
//
//            // Open a connection to the API
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json; utf-8");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setDoOutput(true);
//
//            // Create JSON payload
//            String jsonInputString = String.format("{\"to\": \"%s\", \"subject\": \"%s\", \"message\": \"%s\"}", recipient, subject, message);
//
//            // Send the request
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            // Check the response
//            int code = connection.getResponseCode();
//            if (code == HttpURLConnection.HTTP_OK) {
//                System.out.println("Email sent successfully!");
//            } else {
//                System.out.println("Failed to send email. Response code: " + code);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        sendEmail("recipient@example.com", "Test Subject", "Hello, this is a test email.");
//    }
//}


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AlertSystem {
    static void alert() {
        String to = "mohantyashirvad@gmail.com";
        String subject = "DDOS Attack Detected";
        String body = "A DDOS Attack has been detected and steps have been taken to mitigate and prevent further attacks.";
        System.out.println("DDOS detected!");
        Properties properties = new Properties();
        try {
            // Load the configuration file
            properties.load(new FileInputStream("C:\\Users\\Ashirvad\\OneDrive\\Desktop\\CloudDDoSDefense\\src\\main\\java\\config.properties"));
        } catch (IOException e) {
            System.out.println("Error loading email configuration.");
            e.printStackTrace();
            return;
        }

        final String username = properties.getProperty("email.username");
        final String password = properties.getProperty("email.password");

        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", "smtp.gmail.com");
        emailProperties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
