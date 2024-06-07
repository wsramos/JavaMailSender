package br.com.loriens.javamailsender.domain.ports.interfaces;

import br.com.loriens.javamailsender.domain.entities.Body;
import br.com.loriens.javamailsender.domain.entities.Email;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface SendEmailPort {
    void sendSimpleEmail(Email email) throws TemplateException, IOException;
    void sendEmailMessage(Email email) throws MessagingException, TemplateException, IOException;
    String getBodyFromTemplate(Body body) throws IOException, TemplateException;
}
