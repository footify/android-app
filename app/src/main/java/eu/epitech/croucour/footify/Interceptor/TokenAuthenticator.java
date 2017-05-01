package eu.epitech.croucour.footify.Interceptor;


import java.io.IOException;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.SignInSignUp.SignInSignUpService;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by cleme_000 on 03/03/2016.
 */

public class TokenAuthenticator implements Authenticator {

    final Manager _manager;
    private final TokenEntity _tokenEntity;

    public TokenAuthenticator(TokenEntity tokenEntity, Manager manager) {
        _manager = manager;
        _tokenEntity = tokenEntity;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (_tokenEntity == null) {
            throw new IOException();
        }
        SignInSignUpService signInSignUpService = new SignInSignUpService(null, _manager);

        String newAccessToken = signInSignUpService.refreshTokenSync(_tokenEntity);

        if (newAccessToken == null) {
            throw new IOException();
        }

        _tokenEntity.set_access_token(newAccessToken);

        return response.request().newBuilder().build();
    }
}
