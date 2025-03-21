package org.agendifive.agendamientoapi.service;

import org.agendifive.agendamientoapi.model.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


@Service
public class NotificationService  implements NotificationInterface {

    @Value("${mail.host}")
    private String smtp;
    @Value("${mail.port}")
    private String port;
    @Value("${mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${mail.properties.mail.smtp.auth}")
    private String startls;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Override
    public Boolean sendNotification(NotificationRequest notificationRequest) {
        Boolean response = false;
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", auth);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notificationRequest.getAddressee()));
            message.setSubject(notificationRequest.getSubject());

            // Crear multipart para contenido y adjuntos
            Multipart multipart = new MimeMultipart("related");

            // Parte HTML
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = getEmailTemplate(notificationRequest);
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Parte imagen embebida
            MimeBodyPart imagePart = new MimeBodyPart();
            // Cargar imagen desde resources
            java.net.URL imageUrl = getClass().getClassLoader().getResource("images/logo.jpg");
            if (imageUrl != null) {
                imagePart.attachFile(new java.io.File(imageUrl.toURI()));
                imagePart.setContentID("<logo>");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                multipart.addBodyPart(imagePart);
            }

            message.setContent(multipart);

            // Enviar
            Transport.send(message);
            response = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    // Método para generar la plantilla HTML
    private String getEmailTemplate(NotificationRequest notificationRequest) {
        String template = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }\n" +
                "        .container { background-color: #ffffff; padding: 20px; margin: 50px auto; width: 80%; max-width: 600px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }\n" +
                "        .header { background-color: #4CAF50; text-align: center; border-radius: 8px 8px 0 0; }\n" +
                "        .content { padding: 20px; color: #333333; }\n" +
                "        .footer { text-align: center; font-size: 12px; color: #888888; margin-top: 20px; }\n" +
                "        .logo { max-width: 150px; margin: 10px auto; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"header\">\n" +
                "        <img class=\"logo\" src=\"cid:logo\" alt=\"Logo\" />\n" + // Aquí referenciamos la imagen CID
                "    </div>\n" +
                "    <div class=\"content\">\n" +
                "        <p>" + notificationRequest.getMessage() + "</p>\n" +
                "    </div>\n" +
                "    <div class=\"footer\">\n" +
                "        <p>Este es un mensaje automático, por favor no responder.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        return template;
    }

}
