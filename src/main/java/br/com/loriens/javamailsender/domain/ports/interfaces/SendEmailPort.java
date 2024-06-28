package br.com.loriens.javamailsender.domain.ports.interfaces;

import br.com.loriens.javamailsender.domain.entities.Body;
import br.com.loriens.javamailsender.domain.entities.Email;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface SendEmailPort {
    void sendEmailMessage(Email email);
    String getBodyFromTemplate(Body body);
}
