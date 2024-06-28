package br.com.loriens.javamailsender.domain.ports.interfaces;

import java.io.IOException;

public interface AuthenticationPort {

    Object authenticate() throws IOException;
}
