package br.com.loriens.javamailsender.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Attachment extends FileSystemResource {

    private String name;
    private String attPath;

    public Attachment(File file) {
        super(file);
    }
}
