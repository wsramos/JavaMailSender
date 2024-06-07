package br.com.loriens.javamailsender.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.TimeZone;


@Getter
public class FreeMarkerConfiguration {
    private static FreeMarkerConfiguration instance;
    private final Configuration cfg;

    private FreeMarkerConfiguration() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_33);
        cfg.setDirectoryForTemplateLoading(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("templates")).getFile()));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
    }

    public static FreeMarkerConfiguration getInstance() throws IOException {
        if (instance == null) {
            instance = new FreeMarkerConfiguration();
        }
        return instance;
    }

}