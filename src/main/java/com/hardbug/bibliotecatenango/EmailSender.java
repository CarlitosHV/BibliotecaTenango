package com.hardbug.bibliotecatenango;

import com.hardbug.bibliotecatenango.Models.Email;
import javafx.concurrent.Task;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {
    public EmailSender() {

    }

    public void send(Email email) {
        SendMailTask task = new SendMailTask(email);
        new Thread(task).start();
    }

    private class SendMailTask extends Task<Void> {
        private final Email email;
        private final String from;
        private final String password;

        public SendMailTask(Email email) {
            this.email = email;
            this.from = email.getFrom();
            this.password = email.getPassword();
        }

        @Override
        protected Void call() {
            String host = "smtp-mail.outlook.com";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", "587");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email.getFrom(), IndexApp.username_email));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
                message.setSubject(email.getSubject());
                message.setContent(email.getBody(), "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException | UnsupportedEncodingException mex) {
                mex.printStackTrace();
            }

            return null;
        }
    }
}
