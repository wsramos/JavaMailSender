package br.com.loriens.javamailsender.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyDTO {

    private String to;
    private String from;
    private String subject;
    private String message;
    private String title;
    private String templateName;
}
