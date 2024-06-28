package br.com.loriens.javamailsender.domain.adapters.services;

import br.com.loriens.javamailsender.config.FreeMarkerConfiguration;
import br.com.loriens.javamailsender.domain.entities.Body;
import br.com.loriens.javamailsender.domain.entities.Email;
import br.com.loriens.javamailsender.domain.ports.interfaces.AuthenticationGmailPort;
import br.com.loriens.javamailsender.domain.ports.interfaces.SendEmailPort;
import br.com.loriens.javamailsender.infrastructure.authorization.adapters.services.AuthenticationGmailAdapter;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static br.com.loriens.javamailsender.shared.constants.ApplicationPropertiesConst.APPLICATION_NAME;

public class SendEmailGmailAdapter implements SendEmailPort {

    private final AuthenticationGmailPort authenticationGmailAdapter;

    private final FreeMarkerConfiguration cfg;

    @Autowired
    public SendEmailGmailAdapter() throws IOException {
        this.cfg = FreeMarkerConfiguration.getInstance();
        this.authenticationGmailAdapter = new AuthenticationGmailAdapter();
    }

    @Override
    public void sendEmailMessage(Email email) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), authenticationGmailAdapter.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();


            Message message = createMessage(email);

            message = service.users().messages().send("me", message).execute();
            //Todo verificar se é necessário manter estes prints
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());
        } catch (IOException e) {
            //TODO tratar exceção
            e.printStackTrace();
        } catch (MessagingException | GeneralSecurityException e) {
            //TODO tratar exceção
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBodyFromTemplate(Body body) {
        Map<String, Object> root = new HashMap<>();

        StringWriter result = new StringWriter(1024);

        root.put("body", body);

        try{
            Template template = cfg.getCfg().getTemplate(body.getTemplateName());
            template.process(root, result);
        } catch (IOException | TemplateException e) {
            //TODO tratar exceção
            e.printStackTrace();
        }


        return result.toString();
    }

    public Message createMessage(Email email) throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        Message message = new Message();

        try {
            mimeMessage.setFrom(new InternetAddress(email.getFrom()));
            mimeMessage.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(email.getTo()));
            mimeMessage.setSubject(email.getSubject());
            mimeMessage.setContent(getBodyFromTemplate(email.getBody()), "text/html; charset=UTF-8");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            mimeMessage.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

            message.setRaw(encodedEmail);
        } catch (MessagingException e) {
            //TODO tratar exceção
            e.printStackTrace();
        }

        return message;
    }
}
