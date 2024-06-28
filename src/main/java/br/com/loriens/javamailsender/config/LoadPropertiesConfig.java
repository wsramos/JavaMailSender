package br.com.loriens.javamailsender.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
public class LoadPropertiesConfig {

    private Map<String, Object> properties;

    public LoadPropertiesConfig() {
        loadPropertiesFromYaml();
    }

    private void loadPropertiesFromYaml() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");
        properties = yaml.load(inputStream);
    }
}