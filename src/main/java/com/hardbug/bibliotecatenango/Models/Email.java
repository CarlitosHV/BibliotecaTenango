package com.hardbug.bibliotecatenango.Models;

import com.hardbug.bibliotecatenango.IndexApp;

public class Email {
    private String to;
    private String from;
    private String password;
    private String subject;
    private String body;

    public Email() {
    }

    public Email(String to, String subject, String body) {
        this.setFrom(IndexApp.correo);
        this.setPassword(IndexApp.password_email);
        this.setTo(to);
        this.setSubject(subject);
        this.setBody(body);
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
