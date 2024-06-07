package br.com.loriens.javamailsender.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {
    private String from;
    private String to;
    private String subject;
    private Body body;
    private List<Attachment> attachments;
}
