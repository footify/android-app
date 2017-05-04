package eu.epitech.croucour.footify.Interceptor;


import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cleme_000 on 03/03/2016.
 */
public class AuthInterceptor implements Interceptor {

    private final Manager _manager;
    TokenEntity _tokenEntity;

    public AuthInterceptor(TokenEntity tokenEntity, Manager manager) {
        _tokenEntity = tokenEntity;
        _manager = manager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        setAuthorization(requestBuilder, _tokenEntity);

        Request build = requestBuilder.build();

        Response response = chain.proceed(build);

        if (_tokenEntity != null){
            updateTokenExpire(_tokenEntity);
            _manager._tokenManager.modify(_tokenEntity);
        }
        return response;
    }

    private static void setAuthorization(Request.Builder requestBuilder, TokenEntity tokenEntity) {
        if (tokenEntity == null || Objects.equals(tokenEntity.get_type(), "Basic")) {
            requestBuilder.header("Authorization", Credentials.basic(ServiceGeneratorApi.CLIENT, ServiceGeneratorApi.SECRET));
        } else if (tokenEntity.get_access_token() != null && Objects.equals(tokenEntity.get_type(), "Bearer")){
            requestBuilder.header("Authorization", "Bearer " + tokenEntity.get_access_token());
        }
    }

    public static void updateTokenExpire(TokenEntity tokenEntity) {
        Date date = new Date();
        String expire_in = tokenEntity.get_expire_in();
        long expireToken = date.getTime() + (Long.parseLong(expire_in) * 1000);
        tokenEntity.set_expire_access_token(String.valueOf(expireToken));
    }
}
