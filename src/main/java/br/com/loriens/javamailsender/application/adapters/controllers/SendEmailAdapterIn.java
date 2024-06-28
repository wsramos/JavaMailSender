package br.com.loriens.javamailsender.application.adapters.controllers;

import br.com.loriens.javamailsender.domain.adapters.services.SendEmailGmailAdapter;
import br.com.loriens.javamailsender.domain.dtos.BodyDTO;
import br.com.loriens.javamailsender.domain.entities.Email;
import br.com.loriens.javamailsender.domain.mappers.BodyMapper;
import br.com.loriens.javamailsender.domain.ports.interfaces.SendEmailPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1", produces = "application/json")
public class SendEmailAdapterIn {

    private final SendEmailPort sendEmailPort;

    private final BodyMapper mapper;

    @Autowired
    public SendEmailAdapterIn(BodyMapper mapper) throws IOException {
        this.mapper = mapper;
        sendEmailPort = new SendEmailGmailAdapter();
    }

    @PostMapping("/send-email")
    private ResponseEntity<String> sendEmail(@RequestBody BodyDTO body) {
        Email email = Email.builder()
                .body(mapper.toEntity(body))
                .to(body.getTo())
                .from(body.getFrom())
                .subject(body.getSubject())
                .build();
        try{
            sendEmailPort.sendEmailMessage(email);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error sending email: " + e.getMessage());
        }
        return ResponseEntity.ok("Email sent successfully");
    }
}
