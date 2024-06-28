package br.com.loriens.javamailsender.domain.ports.interfaces;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;

public interface AuthenticationGmailPort extends AuthenticationPort {

    Credential getCredentials(final NetHttpTransport httpTransport) throws IOException;
}
