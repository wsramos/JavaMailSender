package br.com.loriens.javamailsender.domain.adapters.services;

import br.com.loriens.javamailsender.config.FreeMarkerConfiguration;
import br.com.loriens.javamailsender.domain.entities.Attachment;
import br.com.loriens.javamailsender.domain.entities.Body;
import br.com.loriens.javamailsender.domain.entities.Email;
import br.com.loriens.javamailsender.domain.ports.interfaces.SendEmailPort;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @deprecated (This class is deprecated because the authentication method is not allowed in the gmail server.)
 */
@Deprecated(since="0.0.1-SNAPSHOT", forRemoval = true)
public class SendEmailAdapter implements SendEmailPort {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage mailMessage = new SimpleMailMessage();
    private final FreeMarkerConfiguration cfg;
    public SendEmailAdapter(JavaMailSender javaMailSender) throws IOException {
        this.javaMailSender = javaMailSender;
        this.cfg = FreeMarkerConfiguration.getInstance();
    }
    @Override
    public void sendSimpleEmail(Email email) throws TemplateException, IOException {

        mailMessage.setFrom(email.getFrom());
        mailMessage.setTo(email.getTo());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(getBodyFromTemplate(email.getBody()));

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendEmailMessage(Email email) throws MessagingException, TemplateException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(getBodyFromTemplate(email.getBody()), true);

        if(Objects.nonNull(email.getAttachments())){
            for(Attachment attachment : email.getAttachments()){
                helper.addAttachment(attachment.getName(), new FileSystemResource(new File(attachment.getAttPath())));
            }
        }

        javaMailSender.send(message);
    }

    @Override
    public String getBodyFromTemplate(Body body) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();

        StringWriter result = new StringWriter(1024);

        root.put("body", body);

        Template template = cfg.getCfg().getTemplate(body.getTemplateName());
        template.process(root, result);

        return result.toString();
    }
}
