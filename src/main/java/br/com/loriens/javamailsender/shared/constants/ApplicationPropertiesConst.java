package br.com.loriens.javamailsender.shared.constants;

import br.com.loriens.javamailsender.config.LoadPropertiesConfig;
import lombok.Getter;

import java.util.Map;

@Getter
public class ApplicationPropertiesConst {
    public static final String APPLICATION_NAME = String.valueOf(((Map<String, Object>) new LoadPropertiesConfig().getProperties().get("spring")).get("application-name"));
    public static final String CREDENTIAL_FILE_PATH = String.valueOf(((Map<String, Object>) new LoadPropertiesConfig().getProperties().get("gmail")).get("credential-file-path"));
    public static final String TOKENS_DIRECTORY_PATH = String.valueOf(((Map<String, Object>) new LoadPropertiesConfig().getProperties().get("gmail")).get("tokens-directory-path"));

}
