package com.hardbug.bibliotecatenango;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.hardbug.bibliotecatenango.Models.UndertowVerificationCodeReceiver;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.codec.binary.Base64;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;
import static javax.mail.Message.RecipientType.TO;

public class EmailSender {
    private final Gmail service;
    private final String Email = IndexApp.correo;
    public EmailSender() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Biblioteca Pública Municipal Lic. Abel C. Salazar")
                .build();
    }
    public void emailSender(String subject, String Correo, String Mensaje) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(Email));
        email.addRecipient(TO, new InternetAddress(Correo));
        email.setSubject(subject);
        email.setText(Mensaje);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(Objects.requireNonNull(EmailSender.class.getResourceAsStream("/client_secret_1077629672794-dkktlgf9ill6o06vhoah9tl0bt5o3b39.apps.googleusercontent.com.json"))));

        // Build flow and trigger user authorization request if no credentials found.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Collections.singleton(GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();

        Credential credential = flow.loadCredential("user");
        if (!isTokenValid(credential)) {
            UndertowVerificationCodeReceiver receiver = new UndertowVerificationCodeReceiver();
            receiver.startServer();
            String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(receiver.getRedirectUri()).build();
            boolean openBrowser = showAuthorizationUrlAlert(authorizationUrl);
            if (openBrowser) {
                openWebBrowser(authorizationUrl);
            }
            String code = receiver.waitForCode();
            receiver.stop();
            GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(receiver.getRedirectUri()).execute();
            credential = flow.createAndStoreCredential(tokenResponse, "user");
        }


        return credential;
    }

    private static boolean showAuthorizationUrlAlert(String authorizationUrl) {
        Alert alert = new Alertas().CrearAlertaConfirmacion("Autorización o renovación de Token", "Renovando Token.....\nPor favor, continúa el proceso desde el navegador:");
        alert.showAndWait();

        if(alert.getResult() == ButtonType.CANCEL){
            System.exit(0);
        }

        return alert.getResult() == ButtonType.OK;
    }

    private static void openWebBrowser(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (java.io.IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTokenValid(Credential credential) {
        if (credential == null) {
            return false;
        }
        Long tokenExpiresTime = credential.getExpiresInSeconds();
        if (tokenExpiresTime == null || tokenExpiresTime <= 0) {
            return false;
        }
        return true;
    }
}
