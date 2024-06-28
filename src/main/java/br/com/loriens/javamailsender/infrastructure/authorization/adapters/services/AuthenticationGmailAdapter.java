package br.com.loriens.javamailsender.infrastructure.authorization.adapters.services;

import br.com.loriens.javamailsender.domain.ports.interfaces.AuthenticationGmailPort;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import static br.com.loriens.javamailsender.shared.constants.ApplicationPropertiesConst.CREDENTIAL_FILE_PATH;
import static br.com.loriens.javamailsender.shared.constants.ApplicationPropertiesConst.TOKENS_DIRECTORY_PATH;

@Getter
public class AuthenticationGmailAdapter implements AuthenticationGmailPort {

    @Override
    public Object authenticate() throws IOException {
        return GoogleCredentials.getApplicationDefault().createScoped(GmailScopes.GMAIL_READONLY);
    }

    @Override
    public Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        InputStream in = new FileInputStream(CREDENTIAL_FILE_PATH);

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, GsonFactory.getDefaultInstance(), clientSecrets, Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM))
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
