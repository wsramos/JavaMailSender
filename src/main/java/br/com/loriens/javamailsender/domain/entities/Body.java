package br.com.loriens.javamailsender.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Body {
    private String title;
    private String message;
    private Byte imageCode;
    private String templateName;
}
